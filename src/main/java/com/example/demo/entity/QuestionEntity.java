package com.example.demo.entity;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "question")
public class QuestionEntity
{
    @Id
    private String id;
    @NonNull
    private String question;
    @NonNull
    private List<String> options;
    @NonNull
    private String correctAnswer;
    @NonNull
    private String section;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NonNull String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    public @NonNull List<String> getOptions() {
        return options;
    }

    public void setOptions(@NonNull List<String> options) {
        this.options = options;
    }

    public @NonNull String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(@NonNull String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public @NonNull String getSection() {
        return section;
    }

    public void setSection(@NonNull String section) {
        this.section = section;
    }
}
