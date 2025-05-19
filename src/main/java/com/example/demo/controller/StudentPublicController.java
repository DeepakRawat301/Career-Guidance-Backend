package com.example.demo.controller;

import com.example.demo.dto.StudentRegisterDto;
import com.example.demo.dto.StudentVerifyDto;
import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@CrossOrigin("http://localhost:3000/")
public class StudentPublicController
{
    @Autowired
    private StudentService studentService;

    @PostMapping("/student/signup")
    public ResponseEntity<StudentEntity> register(@RequestBody StudentRegisterDto studentRegisterDto)
    {
        StudentEntity studentEntity = studentService.signup(studentRegisterDto);
        return ResponseEntity.ok(studentEntity);
    }

    @PostMapping("/student/verify")
    public ResponseEntity<?>verifyStudent(@RequestBody StudentVerifyDto studentVerifyDto)
    {
        try
        {
            studentService.verifyStudent(studentVerifyDto);
            return ResponseEntity.ok("Student verified successfully");
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/student/resend")
    public ResponseEntity<?>resendOtpStudent(@RequestParam String email)
    {
        try{
            studentService.resendOtp(email);
            return ResponseEntity.ok("Verification code sent");
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
