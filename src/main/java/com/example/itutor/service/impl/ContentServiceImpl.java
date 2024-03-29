package com.example.itutor.service.impl;

import com.example.itutor.config.MyUserDetails;
import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import com.example.itutor.domain.UserActivity;
import com.example.itutor.repository.ContentRepository;
import com.example.itutor.repository.UserActivityRepository;
import com.example.itutor.service.ContentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentServiceI {

    private final ContentRepository contentRepository;
    private final UserActivityRepository userActivityRepository;
    private final EmailSenderServiceImpl emailSenderService;

    @Value("${master.email}")
    String toEmail;

    private long countOfFiles = 0;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, UserActivityRepository userActivityRepository, EmailSenderServiceImpl emailSenderService) {
        this.contentRepository = contentRepository;
        this.userActivityRepository = userActivityRepository;
        this.emailSenderService = emailSenderService;
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
        return null;
    }

    @Override
    public boolean deleteContent(Long id) {
        if (contentRepository.existsById(id)) {
            contentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Content> getContentsByCourseId(Long courseId, Pageable pageable) {
        Page<Content> contents = contentRepository.findByCourseCourseId(courseId, pageable);

        String loggedInUserUsername = getLoggedInUsername();
        LocalDate currentDate = LocalDate.now();
        contents.forEach(content -> {
            // Gets activity for every content part
            UserActivity activity = new UserActivity(currentDate, "Content Viewing", loggedInUserUsername);
            userActivityRepository.save(activity);
        });

        return contents;
    }

    @Override
    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            return ((MyUserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public void fileAdded(){
        countOfFiles++;
        int limit = 10;
        if (countOfFiles % limit == 0){
            emailSenderService.sendEmailWithContent(toEmail, "Files added", limit + " files added. The amount is now " + countOfFiles + ".");
        }
    }

}
