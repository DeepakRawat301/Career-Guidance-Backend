package com.example.demo.controller;

import com.example.demo.dto.AdminRegisterDto;
import com.example.demo.dto.AdminVerifyDto;
import com.example.demo.entity.AdminEntity;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@CrossOrigin("http://localhost:3000/")
public class AdminPublicController
{
    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/signup")
    public ResponseEntity<AdminEntity> register(@RequestBody AdminRegisterDto adminRegisterDto)
    {
        AdminEntity adminEntity = adminService.signup(adminRegisterDto);
        return ResponseEntity.ok(adminEntity);
    }

    @PostMapping("/admin/verify")
    public ResponseEntity<?>verifyAdmin(@RequestBody AdminVerifyDto adminVerifyDto)
    {
        try
        {
            adminService.verifyAdmin(adminVerifyDto);
            return ResponseEntity.ok("Admin verified successfully");
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/admin/resend")
    public ResponseEntity<?>resendOtp(@RequestParam String email)
    {
        try{
            adminService.resendOtp(email);
            return ResponseEntity.ok("Verification code sent");
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
