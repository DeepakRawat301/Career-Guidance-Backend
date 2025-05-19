package com.example.demo.repository;

import com.example.demo.entity.CollegeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository
{
    List<CollegeEntity> findByText(String text);
}
