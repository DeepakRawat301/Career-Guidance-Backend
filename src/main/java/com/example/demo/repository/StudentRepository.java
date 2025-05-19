package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<StudentEntity,String>
{
    StudentEntity findByUsername(String username);
    Optional<StudentEntity> findByMail(String mail);

    List<StudentEntity> findAll();
}
