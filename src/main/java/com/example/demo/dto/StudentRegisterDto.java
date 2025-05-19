package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterDto
{
    private String username;
    private String name;
    private String password;
    private String mail;
    private String tenthBoard;
    private String tenthMarks;
    private String twelfthBoard;
    private String twelfthMarks;
    private String twelfthStream;
    private String anyCompExam;
    private String compExamRank;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTenthBoard() {
        return tenthBoard;
    }

    public void setTenthBoard(String tenthBoard) {
        this.tenthBoard = tenthBoard;
    }

    public String getTenthMarks() {
        return tenthMarks;
    }

    public void setTenthMarks(String tenthMarks) {
        this.tenthMarks = tenthMarks;
    }

    public String getTwelfthBoard() {
        return twelfthBoard;
    }

    public void setTwelfthBoard(String twelfthBoard) {
        this.twelfthBoard = twelfthBoard;
    }

    public String getTwelfthMarks() {
        return twelfthMarks;
    }

    public void setTwelfthMarks(String twelfthMarks) {
        this.twelfthMarks = twelfthMarks;
    }

    public String getTwelfthStream() {
        return twelfthStream;
    }

    public void setTwelfthStream(String twelfthStream) {
        this.twelfthStream = twelfthStream;
    }

    public String getAnyCompExam() {
        return anyCompExam;
    }

    public void setAnyCompExam(String anyCompExam) {
        this.anyCompExam = anyCompExam;
    }

    public String getCompExamRank() {
        return compExamRank;
    }

    public void setCompExamRank(String compExamRank) {
        this.compExamRank = compExamRank;
    }


}
