package com.example.demo.repository;

import com.example.demo.entity.AdminEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<AdminEntity,String>
{
    AdminEntity findByUsername(String username);
    Optional<AdminEntity>findByMail(String mail);

    List<AdminEntity> findAll();
}
