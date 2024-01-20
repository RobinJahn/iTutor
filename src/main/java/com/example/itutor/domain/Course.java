package com.example.itutor.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @ElementCollection
    private List<Long> contents = new ArrayList<>();


    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getContents() {
        return contents;
    }

    public void setContents(List<Long> contents) {
        this.contents = contents;
    }

    public void addContent(Content content) {
        contents.add(content.getContentID());
        content.setCourse(this); // Set the course reference in the content
    }
}
