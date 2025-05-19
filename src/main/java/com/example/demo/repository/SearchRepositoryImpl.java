package com.example.demo.repository;

import com.example.demo.entity.CollegeEntity;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SearchRepositoryImpl implements SearchRepository{

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    @Override
    public List<CollegeEntity> findByText(String text) {

        final List<CollegeEntity> collegeEntities=new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("Career_Guidance");
        MongoCollection<Document> collection = database.getCollection("college");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList
                (new Document("$search",
                                new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("name", "ranking", "eligibilityCriteria", "facilities", "coursesOfferedWithFees", "location", "accreditation")))),
                        new Document("$sort", new Document("ranking", 1L))));


        result.forEach(document -> collegeEntities.add(mongoConverter.read(CollegeEntity.class,document)));

        return collegeEntities;
    }
}
