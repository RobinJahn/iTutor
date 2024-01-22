package com.example.itutor.controller;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import com.example.itutor.service.ContentServiceI;
import com.example.itutor.service.CourseServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseWebController {
    private final CourseServiceI courseService;
    private final ContentServiceI contentService;

    public CourseWebController(CourseServiceI courseService, ContentServiceI contentService) {
        this.courseService = courseService;
        this.contentService = contentService;
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



}
