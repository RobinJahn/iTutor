package com.example.itutor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "content")
public class Content implements Serializable {

    public class ContentType {
        public static final String TEXT = "text";
        public static final String DOCUMENT = "document";
    }

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false, length = 10000)
    private String contentData;


    @Column(nullable = true)
    private String fileName;

    @Column(nullable = true)
    private String mimeType;

    @Column(nullable = true)
    private String url;

    @Lob
    @Column(nullable = true)
    private byte[] contentByteData;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;



    public String getContentAsString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Content");
        sb.append(contentID);
        sb.append(": ");
        sb.append(title);
        sb.append("\n");
        if (contentType.equals(ContentType.TEXT)) {
            sb.append(contentData);
            sb.append("\n");
            return sb.toString();
        }
        return sb.toString();
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
