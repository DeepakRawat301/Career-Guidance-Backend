package com.example.demo.service;

import com.example.demo.entity.CollegeEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.entity.StudentTestRecordEntity;
import com.example.demo.repository.CollegeRepository;
import com.example.demo.repository.StudentTestRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EligibilityService
{
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private StudentTestRecordRepository studentTestRecordRepository;


    // Fetch the test record for the student

    public List<CollegeEntity> checkEligibility(StudentEntity studentEntity) {
        List<StudentTestRecordEntity> testRecords = studentTestRecordRepository.findByStudentUsername(studentEntity.getUsername());
        if (testRecords == null || testRecords.isEmpty()) {
            throw new IllegalArgumentException("Test record not found for student: " + studentEntity.getUsername());
        }

        StudentTestRecordEntity testRecord = testRecords.get(0);

        double scorePercentage = testRecord.getScorePercentage();
        String tenthMarks = studentEntity.getTenthMarks();
        String twelfthMarks = studentEntity.getTwelfthMarks();
        String compExamRank = studentEntity.getCompExamRank();

        if (!isEligibleBasedOnMarks(tenthMarks, twelfthMarks) || !isEligibleBasedOnExam(compExamRank, scorePercentage)) {
            // Instead of throwing exception, return empty list
            return Collections.emptyList();
        }

        List<CollegeEntity> colleges = collegeRepository.findAll();
        List<CollegeEntity> eligibleColleges = colleges.stream()
                .filter(college -> isEligibleForCollege(college, scorePercentage, studentEntity))
                .collect(Collectors.toList());

        // Return empty list if none found
        return eligibleColleges;
    }

    // Check eligibility based on 10th and 12th marks

    private boolean isEligibleBasedOnMarks(String tenthMarks, String twelfthMarks) {
        try {
            if (tenthMarks == null || twelfthMarks == null) return false;

            double tenthMarksPercentage = Double.parseDouble(tenthMarks.trim());
            double twelfthMarksPercentage = Double.parseDouble(twelfthMarks.trim());

            return (tenthMarksPercentage >= 80 && tenthMarksPercentage <= 90) &&
                    (twelfthMarksPercentage >= 85 && twelfthMarksPercentage <= 95);
        } catch (NumberFormatException e) {
            System.err.println("Invalid mark format: " + e.getMessage());
            return false;
        }
    }


    // Check eligibility based on competitive exam rank and aptitude test score

    private boolean isEligibleBasedOnExam(String compExamRank, double aptitudeTestScore) {
        try {
            if (compExamRank == null || compExamRank.trim().isEmpty()) {
                return aptitudeTestScore >= 90;
            }
            int rank = Integer.parseInt(compExamRank.trim());
            return rank <= 1000 && aptitudeTestScore >= 90;
        } catch (NumberFormatException e) {
            System.err.println("Invalid rank format: " + e.getMessage());
            return false;
        }
    }


    // Method to check if the student is eligible for a college based on college ranking and other criteria

    private boolean isEligibleForCollege(CollegeEntity college, double aptitudeTestScore, StudentEntity studentEntity) {
        // College ranking should be between 1 and 5
        boolean isEligibleByRanking = college.getRanking() >= 1 && college.getRanking() <= 5;

        // You can add more logic to match the subject criteria or any other filters based on the college's eligibility rules

        return isEligibleByRanking;
    }



}
