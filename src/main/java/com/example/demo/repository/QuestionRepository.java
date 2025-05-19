package com.example.demo.repository;

import com.example.demo.entity.QuestionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionEntity,String>
{
    List<QuestionEntity> findAll();

    Optional<QuestionEntity> findById(String id);
}
