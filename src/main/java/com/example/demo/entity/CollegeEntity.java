package com.example.demo.entity;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "college")
public class CollegeEntity
{
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String location;
    @NonNull
    private int ranking;
    @NonNull
    private List<String> accreditation;
    @NonNull
    private List<EligibilityCriteria> eligibilityCriteria;
    @NonNull
    private List<CourseOfferedWithFee> coursesOfferedWithFees;
    @NonNull
    private List<String> facilities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public @NonNull List<String> getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(@NonNull List<String> accreditation) {
        this.accreditation = accreditation;
    }

    public @NonNull List<EligibilityCriteria> getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(@NonNull List<EligibilityCriteria> eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public @NonNull List<CourseOfferedWithFee> getCoursesOfferedWithFees() {
        return coursesOfferedWithFees;
    }

    public void setCoursesOfferedWithFees(@NonNull List<CourseOfferedWithFee> coursesOfferedWithFees) {
        this.coursesOfferedWithFees = coursesOfferedWithFees;
    }

    public @NonNull List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(@NonNull List<String> facilities) {
        this.facilities = facilities;
    }
}
