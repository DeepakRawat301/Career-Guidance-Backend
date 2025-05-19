package com.example.demo.repository;

import com.example.demo.dto.CollegeSummaryDto;
import com.example.demo.entity.CollegeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends MongoRepository<CollegeEntity,String>
{
    List<CollegeEntity> findAll();

    Optional<CollegeEntity> findById(String id);

    // Native Mongo Query for search
    @Query("{ '$or': [ " +
            "{ 'name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'location': { $regex: ?0, $options: 'i' } }, " +
            "{ 'accreditation': { $elemMatch: { $regex: ?0, $options: 'i' } } }, " +
            "{ 'coursesOfferedWithFees.courseName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'facilities': { $elemMatch: { $regex: ?0, $options: 'i' } } } " +
            "] }")
    List<CollegeEntity> searchColleges(String keyword);

    @Query(value = "{}", fields = "{ 'name' : 1, 'location' : 1, 'ranking' : 1 }")
    List<CollegeSummaryDto> findCollegeSummaries();

    @Query("{ 'ranking' : { $lte: ?0 }, 'coursesOfferedWithFees.tuitionFee' : { $lte: ?1 } }")
    List<CollegeEntity> findByRankingAndFee(int maxRanking, int maxFee);




}
