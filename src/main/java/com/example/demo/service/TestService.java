package com.example.demo.service;

import com.example.demo.dto.TestResult;
import com.example.demo.dto.TestSubmissionRequest;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.entity.StudentTestRecordEntity;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentTestRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestService
{
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentTestRecordRepository studentTestRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    public TestResult submitTest(List<TestSubmissionRequest> submissions, String username) {
        StudentEntity student = studentRepository.findByUsername(username);

        int correct = 0;

        for (TestSubmissionRequest submission : submissions) {
            QuestionEntity question = questionRepository.findById(submission.getQuestionId()).orElse(null);

            if (question != null &&
                    question.getCorrectAnswer().equalsIgnoreCase(submission.getSelectedOption())) {
                correct++;
            }
        }

        int total = submissions.size();
        int wrong = total - correct;
        double percentage = (correct * 100.0) / total;

        String message;
        if (percentage >= 80) {
            message = "Excellent performance!";
        } else if (percentage >= 60) {
            message = "Good performance!";
        } else {
            message = "Needs Improvement.";
        }

        // Save record
        StudentTestRecordEntity record = new StudentTestRecordEntity();
        record.setId(UUID.randomUUID().toString());
        record.setStudentUsername(student.getUsername()); // Correct way
        record.setTotalQuestions(total);
        record.setCorrectAnswers(correct);
        record.setWrongAnswers(wrong);
        record.setScorePercentage(percentage);
        record.setPerformanceMessage(message);

        studentTestRecordRepository.save(record);

        // Prepare result
        TestResult result = new TestResult();
        result.setTotalQuestions(total);
        result.setCorrectAnswers(correct);
        result.setWrongAnswers(wrong);
        result.setScorePercentage(percentage);
        result.setPerformanceMessage(message);

        return result;
    }

}
