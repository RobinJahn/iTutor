package com.example.itutor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Content> contents = new ArrayList<>();


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

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void addContent(Content content) {
        contents.add(content);
        content.setCourse(this);
    }

    public boolean removeContent(Long contentId) {
        boolean success = false;
        for (Content content : contents) {
            if (content.getContentID().equals(contentId)) {
                contents.remove(content);
                content.setCourse(null);
                success = true;
                break;
            }
        }
        return success;
    }

    public String getCourseAsText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(title).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Contents: ").append("\n");
        for (Content content : contents) {
            sb.append(content.getContentAsString()).append("\n");
        }
        return sb.toString();
    }
}
