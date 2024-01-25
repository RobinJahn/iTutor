package com.example.itutor.controller;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import com.example.itutor.service.ContentServiceI;
import com.example.itutor.service.CourseServiceI;
import com.example.itutor.service.impl.GCPStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseWebController {
    private final CourseServiceI courseService;
    private final ContentServiceI contentService;
    private final GCPStorageService storageService;

    public CourseWebController(CourseServiceI courseService, ContentServiceI contentService, GCPStorageService storageService) {
        this.courseService = courseService;
        this.contentService = contentService;
        this.storageService = storageService;
    }

    @GetMapping
    public String getAllCourses(Model model, @PageableDefault(size = 7) Pageable pageable) {
        model.addAttribute("coursesPage", courseService.getAllCourses(pageable));
        return "courses/coursesList"; // Thymeleaf template name for listing courses
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id,
                                Model model,
                                @PageableDefault(size = 7) Pageable pageable) {

        Course course = courseService.getCourseById(id).orElse(null);
        if (course == null) {
            return "error";
        }

        Page<Content> contents = contentService.getContentsByCourseId(id, pageable);
        model.addAttribute("contents", contents);
        model.addAttribute("course", course);

        return "courses/course";
    }


    @PostMapping("/create")
    public String createCourse(@RequestParam String title, @RequestParam String description, RedirectAttributes redirectAttributes) {
        Course newCourse = new Course();
        newCourse.setTitle(title);
        newCourse.setDescription(description);
        courseService.createCourse(newCourse);
        redirectAttributes.addFlashAttribute("success", "Course created successfully!");
        return "redirect:/courses";
    }

    @PostMapping("/addContent")
    public String addContentToCourse(@RequestParam Long courseId, @RequestParam String contentTitle, @RequestParam String contentData, RedirectAttributes redirectAttributes) {
        // Retrieve the course by ID
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Create a new content object
            Content newContent = new Content();
            newContent.setTitle(contentTitle);
            newContent.setContentType(Content.ContentType.TEXT);
            newContent.setContentData(contentData);

            //Save content in the database
            contentService.createContent(newContent);

            // Add the new content to the course
            course.addContent(newContent); // Assuming Course class has an addContent method

            // Update the course with the new content
            courseService.updateCourse(courseId, course);

            redirectAttributes.addFlashAttribute("success", "Content added successfully!");
            return "redirect:/courses/" + courseId;
        } else {
            redirectAttributes.addFlashAttribute("error", "Course not found!");
            return "redirect:/courses";
        }
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam Long courseId,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("fileDescription") String fileDescription, // Capture file description
                             RedirectAttributes attributes, Model model) {
        System.out.println("Uploading file: " + file.getOriginalFilename());
        try {
            // Retrieve the course by ID
            Optional<Course> optionalCourse = courseService.getCourseById(courseId);

            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();

                Content content = getContentForFile(file, fileDescription, course);
                content.setUrl(storageService.uploadDocument("i-tutor", file.getOriginalFilename(), file.getBytes()));
                model.addAttribute("documentName", file.getOriginalFilename());
                // Save content to your database
                contentService.createContent(content);

                // Add the new content to the course
                course.addContent(content);

                // Update the course with the new content
                courseService.updateCourse(courseId, course);

                attributes.addFlashAttribute("success", "File uploaded successfully!");
                return "upload";
            } else {
                attributes.addFlashAttribute("error", "Course not found!");
                return "redirect:/courses";
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "File upload failed!");
            return "redirect:/courses";
        }
    }

    private static Content getContentForFile(MultipartFile file, String fileDescription, Course course) throws IOException {
        Content content = new Content();
        content.setTitle(file.getOriginalFilename());
        content.setContentType(Content.ContentType.DOCUMENT);
        content.setContentData(fileDescription);
        content.setContentByteData(file.getBytes());
        content.setFileName(file.getOriginalFilename());
        content.setMimeType(file.getContentType());

        // Associate content with the course
        content.setCourse(course);
        return content;
    }


    @GetMapping("/files/{contentId}")
    public ResponseEntity<Resource> getFile(@PathVariable Long contentId) {
        try {
            Content content = contentService.getContentById(contentId).isPresent() ? contentService.getContentById(contentId).get() : null;

            if (content != null) {
                ByteArrayResource resource = new ByteArrayResource(content.getContentByteData());

                String contentType = content.getMimeType() != null ? content.getMimeType() : "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + content.getFileName() + "\"")
                        .body(resource);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/removeContent")
    public String removeContentFromCourse(@RequestParam Long courseId, @RequestParam Long contentId, RedirectAttributes redirectAttributes) {
        // Retrieve the course by ID
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Remove the content from the course
            boolean success = course.removeContent(contentId);

            if (!success) {
                redirectAttributes.addFlashAttribute("error", "Content not found!");
                return "redirect:/courses/" + courseId;
            }

            // Update the course with the new content
            contentService.deleteContent(contentId);
            courseService.updateCourse(courseId, course);

            redirectAttributes.addFlashAttribute("success", "Content removed successfully!");
            return "redirect:/courses/" + courseId;
        } else {
            redirectAttributes.addFlashAttribute("error", "Course not found!");
            return "redirect:/courses";
        }
    }

    @PostMapping("/courses/updateContent")
    public ResponseEntity<?> updateContent(@RequestBody Content content) {
        System.out.println("updateContent");
        //get content to update
        Optional<Content> contentToUpdate = contentService.getContentById(content.getContentID());
        if (contentToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //update tile and description
        contentToUpdate.get().setTitle(content.getTitle());
        contentToUpdate.get().setContentData(content.getContentData());

        Content updatedContent = contentService.updateContent(content.getContentID(), contentToUpdate.get());
        return updatedContent != null ? ResponseEntity.ok(updatedContent) : ResponseEntity.notFound().build();
    }

}
