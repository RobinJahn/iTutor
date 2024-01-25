package com.example.itutor.service.impl;

import com.example.itutor.domain.Content;
import com.example.itutor.repository.ContentRepository;
import com.example.itutor.repository.CourseRepository;
import com.example.itutor.service.ContentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentServiceI {

    private final ContentRepository contentRepository;
    private final GCPStorageService storageService;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, GCPStorageService storageService) {
        this.contentRepository = contentRepository;
        this.storageService = storageService;
    }

    @Override
    public Page<Content> getAllContents(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

    @Override
    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    @Override
    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public Content updateContent(Long id, Content updatedContent) {
        if (contentRepository.existsById(id)) {
            updatedContent.setContentID(id);
            return contentRepository.save(updatedContent);
        }
        return null; // Or Error-Handling
    }

    @Override
    public String uploadContentDocument(String bucketName, MultipartFile file, String title, String contentType) {
        try {
            byte[] content = file.getBytes();

            // Save Meta-Data
            Content contentEntity = new Content();
            contentEntity.setTitle(title);
            contentEntity.setContentType(contentType);
            contentEntity.setContentData(storageService.uploadDocument(bucketName, file.getOriginalFilename(), content));

            return "Upload successful. Document link: " + contentEntity.getContentData();
        } catch (IOException e) {
            return "Error uploading document: " + e.getMessage();
        }
    }

    @Override
    public boolean deleteContent(Long id) {
        if (contentRepository.existsById(id)) {
            contentRepository.deleteById(id);
            return true;
        }
        return false; // Or Error-Handling
    }

    @Override
    public Page<Content> getContentsByCourseId(Long courseId, Pageable pageable) {
        return contentRepository.findByCourseCourseId(courseId, pageable);
    }


}
