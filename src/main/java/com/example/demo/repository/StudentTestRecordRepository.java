package com.example.demo.repository;

import com.example.demo.entity.StudentTestRecordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTestRecordRepository extends MongoRepository<StudentTestRecordEntity,String>
{
    List<StudentTestRecordEntity> findByStudentUsername(String username);
}
