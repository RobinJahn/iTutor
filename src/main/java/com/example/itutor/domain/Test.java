package com.example.itutor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    private String testName;
    @ManyToOne
    private Expert createdBy;

    @OneToMany(mappedBy = "test")
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Expert getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Expert createdBy) {
        this.createdBy = createdBy;
    }


    public List<Question> getQuestions() {
        return questions;
    }


    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setTest(this);
    }
}
