package com.example.demo.controller;

import com.example.demo.entity.AdminEntity;
import com.example.demo.entity.CollegeEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.service.AdminService;
import com.example.demo.service.CollegeService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000/")
public class AdminController
{
    @Autowired
    private AdminService adminService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmin() {
        List<AdminEntity> student = adminService.getAllAdmin();
        return ResponseEntity.ok(student);
    }

    @PostMapping("/addCollege")
    public ResponseEntity<CollegeEntity>addCollege(@RequestBody CollegeEntity collegeEntity)
    {
        CollegeEntity college=collegeService.addCollege(collegeEntity);
        return ResponseEntity.ok(college);
    }

    @GetMapping("/allCollege")
    public ResponseEntity<?> getAllCollege()
    {
        List<CollegeEntity>college=collegeService.getAll();
        return ResponseEntity.ok(college);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<QuestionEntity>addQuestion(@RequestBody QuestionEntity questionEntity)
    {
        QuestionEntity question=questionService.addQuestion(questionEntity);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/allQuestion")
    public ResponseEntity<?>getAllQuestion()
    {
        List<QuestionEntity>question=questionService.getAll();
        return ResponseEntity.ok(question);
    }

    @GetMapping("/searchByUsername")
    public ResponseEntity<?> searchByUserName(@RequestParam String username) {
        AdminEntity admin = adminService.findByUsername(username);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestBody AdminEntity updatedAdmin) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        AdminEntity userDB=adminService.findByUsername(username);
        if(userDB!=null)
        {
            boolean success = adminService.updateAdminDetails(username, updatedAdmin);
            if (success) {
                return ResponseEntity.ok("Admin details updated successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin not found or email not verified.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAdmin() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        boolean deleted = adminService.deleteAdminByUsername(authentication.getName());
        if (deleted) {
            return ResponseEntity.ok("Admin deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }



    @PutMapping("/college/update/{id}")
    public ResponseEntity<?> updateCollege(@PathVariable String id, @RequestBody CollegeEntity updatedCollege) {
        Optional<CollegeEntity> userDB = collegeService.findById(id);
        if (userDB.isPresent()) {
            boolean success = collegeService.updateCollege(id, updatedCollege);
            if (success) {
                return ResponseEntity.ok("College details updated successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("College not found or details not saved.");
    }

    @DeleteMapping("/college/delete/{id}")
    public ResponseEntity<?> deleteCollege(@PathVariable String id) {
        Optional<CollegeEntity> userDB = collegeService.findById(id);
        if (userDB.isPresent()) {
            boolean deleted = collegeService.deleteCollegeById(id);
            if (deleted) {
                return ResponseEntity.ok("College deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the college.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("College not found.");
    }


    @GetMapping("/college/searchById")
    public ResponseEntity<?> searchById(@RequestParam String id) {
        Optional<CollegeEntity> college = collegeService.findById(id);
        if (college.isPresent()) {
            return ResponseEntity.ok(college.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("College not found.");
    }

    @PutMapping("/question/update/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable String id, @RequestBody QuestionEntity updatedQuestion) {
        Optional<QuestionEntity> userDB = questionService.findById(id);
        if (userDB.isPresent()) {
            boolean success = questionService.updateQuestion(id, updatedQuestion);
            if (success) {
                return ResponseEntity.ok("Question updated successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question not found or details not saved.");
    }

    @DeleteMapping("/question/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String id) {
        Optional<QuestionEntity> userDB = questionService.findById(id);
        if (userDB.isPresent()) {
            boolean deleted = questionService.deleteQuestionById(id);
            if (deleted) {
                return ResponseEntity.ok("Question deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the question.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
    }


    @GetMapping("/question/searchById")
    public ResponseEntity<?> searchByQId(@RequestParam String id) {
        Optional<QuestionEntity> question = questionService.findById(id);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
    }

    @GetMapping("/allStudents")
    public ResponseEntity<?>getAllStudents()
    {
        List<StudentEntity>allStudents=studentService.getAllStudent();
        return ResponseEntity.ok(allStudents);
    }




}
