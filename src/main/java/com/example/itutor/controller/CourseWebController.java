package com.example.itutor.controller;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import com.example.itutor.service.CourseServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseWebController {
    private final CourseServiceI courseService;

    public CourseWebController(CourseServiceI courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/coursesList"; // Thymeleaf template name for listing courses
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElse(null); // Handle the case where the course is not found
        model.addAttribute("course", course);
        return course != null ? "courses/course" : "error"; // Thymeleaf template for course details or error
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
