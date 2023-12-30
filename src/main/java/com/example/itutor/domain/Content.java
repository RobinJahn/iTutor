package com.example.itutor.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "content")
public class Content implements Serializable {

    public class ContentType {
        public static final String TEXT = "text";
        public static final String VIDEO = "video";
        public static final String AUDIO = "audio";
        public static final String IMAGE = "image";
        public static final String PDF = "pdf";
    }

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false, length = 10000) // Adjust length based on expected content size
    private String contentData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Reference to the Course

    // Getters and Setters
    public Long getContentID() {
        return contentID;
    }

    public void setContentID(Long contentID) {
        this.contentID = contentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Content {" +
                "contentID=" + contentID +
                ", title='" + title + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentData='" + contentData + '\'' +
                '}';
    }
}
