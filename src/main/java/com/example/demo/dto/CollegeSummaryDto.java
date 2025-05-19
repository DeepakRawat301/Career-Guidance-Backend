package com.example.demo.dto;

public class CollegeSummaryDto
{
    private String name;
    private String location;
    private int ranking;

    public CollegeSummaryDto(String name, String location, int ranking) {
        this.name = name;
        this.location = location;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
