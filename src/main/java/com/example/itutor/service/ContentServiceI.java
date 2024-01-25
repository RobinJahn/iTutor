package com.example.itutor.service;

import com.example.itutor.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ContentServiceI {

    Page<Content> getAllContents(Pageable pageable);
    Optional<Content> getContentById(Long id);
    Content createContent(Content content);
    Content updateContent(Long id, Content updatedContent);
    boolean deleteContent(Long id);
    Page<Content> getContentsByCourseId(Long courseId, Pageable pageable);
    String getLoggedInUsername();
}
