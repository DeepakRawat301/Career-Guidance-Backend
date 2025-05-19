package com.example.demo.entity;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "student")
public class StudentEntity
{
    @Id
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private String mail;
    @NonNull
    private String tenthBoard;
    @NonNull
    private String tenthMarks;
    @NonNull
    private String twelfthBoard;
    @NonNull
    private String twelfthMarks;
    @NonNull
    private String twelfthStream;

    private String anyCompExam;
    private String compExamRank;

    private String otp;
    private boolean emailVerified=false;
    private LocalDateTime otpExpiredAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public @NonNull String getMail() {
        return mail;
    }

    public void setMail(@NonNull String mail) {
        this.mail = mail;
    }

    public @NonNull String getTenthBoard() {
        return tenthBoard;
    }

    public void setTenthBoard(@NonNull String tenthBoard) {
        this.tenthBoard = tenthBoard;
    }

    public @NonNull String getTenthMarks() {
        return tenthMarks;
    }

    public void setTenthMarks(@NonNull String tenthMarks) {
        this.tenthMarks = tenthMarks;
    }

    public @NonNull String getTwelfthBoard() {
        return twelfthBoard;
    }

    public void setTwelfthBoard(@NonNull String twelfthBoard) {
        this.twelfthBoard = twelfthBoard;
    }

    public @NonNull String getTwelfthMarks() {
        return twelfthMarks;
    }

    public void setTwelfthMarks(@NonNull String twelfthMarks) {
        this.twelfthMarks = twelfthMarks;
    }

    public @NonNull String getTwelfthStream() {
        return twelfthStream;
    }

    public void setTwelfthStream(@NonNull String twelfthStream) {
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public LocalDateTime getOtpExpiredAt() {
        return otpExpiredAt;
    }

    public void setOtpExpiredAt(LocalDateTime otpExpiredAt) {
        this.otpExpiredAt = otpExpiredAt;
    }

    public StudentEntity(String username, @NonNull String name, @NonNull String password, @NonNull String mail, @NonNull String tenthBoard, @NonNull String tenthMarks, @NonNull String twelfthBoard, @NonNull String twelfthMarks, @NonNull String twelfthStream, String anyCompExam, String compExamRank) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.tenthBoard = tenthBoard;
        this.tenthMarks = tenthMarks;
        this.twelfthBoard = twelfthBoard;
        this.twelfthMarks = twelfthMarks;
        this.twelfthStream = twelfthStream;
        this.anyCompExam = anyCompExam;
        this.compExamRank = compExamRank;
    }


}
