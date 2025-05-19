package com.example.demo.service;

import com.example.demo.entity.AdminEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AdminEntity admin=adminRepository.findByUsername(username);
        if(admin!=null && admin.isEmailVerified())
        {
            return new User(admin.getUsername(), admin.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_Admin")));
        }
        StudentEntity student=studentRepository.findByUsername(username);
        if(student!=null && student.isEmailVerified())
        {
            return new User(student.getUsername(), student.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_Student")));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
