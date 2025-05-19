package com.example.demo.controller;

import com.example.demo.dto.TestResult;
import com.example.demo.dto.TestSubmissionRequest;
import com.example.demo.entity.AdminEntity;
import com.example.demo.entity.CollegeEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.SearchRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin("http://localhost:3000/")
public class StudentController
{
    @Autowired
    private StudentService studentService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TestService testService;
    @Autowired
    private EligibilityService eligibilityService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SearchRepository searchRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudent() {
        List<StudentEntity> student = studentService.getAllStudent();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/allCollege")
    public ResponseEntity<?> getAllCollege()
    {
        List<CollegeEntity>college=collegeService.getAll();
        return ResponseEntity.ok(college);
    }

    @GetMapping("/allQuestion")
    public ResponseEntity<?>getAllQuestion()
    {
        List<QuestionEntity>question=questionService.getAll();
        return ResponseEntity.ok(question);
    }

    @PostMapping("/submitTest")
    public TestResult submitTest(@RequestBody List<TestSubmissionRequest> submissions) {
        // Get username from logged-in user session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        return testService.submitTest(submissions, username);
    }



//    @GetMapping("/check")
//    public List<CollegeEntity> checkEligibility(@AuthenticationPrincipal User user) {
//        // Step 1: Get the logged-in student's username
//        String username = user.getUsername();
//
//        // Step 2: Fetch student data from the database based on the logged-in username
//        StudentEntity studentEntity = studentRepository.findByUsername(username);
//        if (studentEntity == null) {
//            throw new IllegalStateException("Student not found: " + username);
//        }
//
//        // Step 3: Call the eligibility service to get the list of eligible colleges
//        return eligibilityService.checkEligibility(studentEntity);
//    }

    @GetMapping("/check")
    public ResponseEntity<?> checkEligibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        StudentEntity studentEntity = studentRepository.findByUsername(username);
        if (studentEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with username: " + username);
        }

        List<CollegeEntity> eligibleColleges;
        try {
            eligibleColleges = eligibilityService.checkEligibility(studentEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        if (eligibleColleges.isEmpty()) {
            // Return 200 with empty list or a message
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(eligibleColleges);
    }


    @GetMapping("/sort")
    public ResponseEntity<?> getSortedAndPaginatedColleges(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<CollegeEntity> colleges = collegeService.getCollegesSortedAndPaginated(sortBy, direction, page, size);

        if (colleges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No colleges found with given sorting criteria.");
        }

        return ResponseEntity.ok(colleges);
    }


//    @GetMapping("/search")
//    public ResponseEntity<?> searchCollegesSortedPaginated(
//            @RequestParam(defaultValue = "") String keyword,
//            @RequestParam(defaultValue = "name") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        if (keyword.isEmpty()) {
//            return ResponseEntity.badRequest()
//                    .body("Keyword is required for searching colleges.");
//        }
//
//        List<CollegeEntity> colleges = collegeService.searchCollegesSortedPaginated(keyword, sortBy, direction, page, size);
//
//        if (colleges.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("No colleges found matching your search keyword: " + keyword);
//        }
//
//        return ResponseEntity.ok(colleges);
//    }


    @GetMapping("/filter")
    public ResponseEntity<?> filterCollegesByRankingAndFee(
            @RequestParam int maxRanking,
            @RequestParam int maxFee
    ) {
        List<CollegeEntity> colleges = collegeService.filterCollegesByRankingAndFee(maxRanking, maxFee);

        if (colleges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No colleges found matching your ranking and fee criteria.");
        }

        return ResponseEntity.ok(colleges);
    }

    @GetMapping("/searchByText/{text}")
    public List<CollegeEntity>searchByWord(@PathVariable String text)
    {
        return searchRepository.findByText(text);
    }

    @GetMapping("searchCollegeBy/{keyword}")
    public List<CollegeEntity>searchByKey(@PathVariable String keyword)
    {
        return collegeService.searchColleges((keyword));
    }

    @GetMapping("/searchByUsername")
    public ResponseEntity<?> searchByUserName(@RequestParam String username) {
        StudentEntity student = studentService.findByUsername(username);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateStudent(@PathVariable String username, @RequestBody StudentEntity updatedStudent) {
        StudentEntity userDB=studentService.findByUsername(username);
        if(userDB!=null)
        {
            boolean success = studentService.updateStudentDetails(username, updatedStudent);
            if (success) {
                return ResponseEntity.ok("Student details updated successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found or email not verified.");
    }

//    @DeleteMapping("/delete/{username}")
//    public ResponseEntity<?> deleteStudent(@PathVariable String username) {
//        StudentEntity userDB=studentService.findByUsername(username);
//        if(userDB!=null)
//        {
//            boolean deleted=studentService.deleteStudentByUsername(username);
//            if(deleted)
//            {
//                return ResponseEntity.ok("Student deleted successfully.");
//            }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found or email not verified.");
//
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        boolean deleted = studentService.deleteStudentByUsername(authentication.getName());
        if (deleted) {
            return ResponseEntity.ok("Student deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }




}
