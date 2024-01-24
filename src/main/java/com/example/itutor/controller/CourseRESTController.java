package com.example.itutor.controller;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import com.example.itutor.service.ContentServiceI;
import com.example.itutor.service.CourseServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseRESTController {
    private final CourseServiceI courseService;
    private final ContentServiceI contentService;

    //@Autowired
    public CourseRESTController(CourseServiceI courseService, ContentServiceI contentService) {
        this.courseService = courseService;
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(size = 10) Pageable pageable) {
        System.out.println("getAllCourses");
        Page<Course> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        System.out.println("getCourseById");
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        System.out.println("createCourse");

        //set the id to null so that it is created
        course.setCourseId(null);
        //empty the contents as they aren't able to be added here
        course.setContents(new ArrayList<>());

        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        System.out.println("updateCourse");
        //get course to update
        Optional<Course> courseToUpdate = courseService.getCourseById(id);
        if (courseToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //update tile and description
        courseToUpdate.get().setTitle(course.getTitle());
        courseToUpdate.get().setDescription(course.getDescription());

        Course updatedCourse = courseService.updateCourse(id, courseToUpdate.get());
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        System.out.println("deleteCourse");
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //Content ----------------------------------------------------------------------------------------------------------

    @GetMapping("/{id}/contents")
    public ResponseEntity<List<Content>> getContentsByCourseId(@PathVariable Long id) {
        System.out.println("getContentsByCourseId");
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course.get().getContents());
    }

    @GetMapping("/{id}/contents/{contentId}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id, @PathVariable Long contentId) {
        System.out.println("getContentById");
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Content> content = contentService.getContentById(contentId);
        return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/contents")
    public ResponseEntity<Course> addContentToCourse(@PathVariable Long id, @RequestBody Content content) {
        System.out.println("addContentToCourse");
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //set the id to null so that it is created
        content.setContentID(null);
        //set the course to the one we are adding to
        content.setCourse(course.get());
        //save new content
        Content createdContent = contentService.createContent(content);

        //add the content to the course
        course.get().addContent(createdContent);

        return ResponseEntity.ok(course.get());
    }

    @PutMapping("/{id}/contents/{contentId}")
    public ResponseEntity<Course> updateContentInCourse(@PathVariable Long id, @PathVariable Long contentId, @RequestBody Content content) {
        System.out.println("updateContentInCourse");
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //get the content to update
        Optional<Content> contentToUpdate = contentService.getContentById(contentId);
        if (contentToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //update the content
        if (content.getTitle() != null && !content.getTitle().isEmpty()) {
            contentToUpdate.get().setTitle(content.getTitle());
        }
        if (content.getContentType() != null && !content.getContentType().isEmpty()) {
            contentToUpdate.get().setContentType(content.getContentType());
        }
        if (content.getContentData() != null && !content.getContentData().isEmpty()) {
            contentToUpdate.get().setContentData(content.getContentData());
        }

        //save the updated content
        Content updatedContent = contentService.updateContent(contentId, contentToUpdate.get());

        return ResponseEntity.ok(course.get());
    }

    @DeleteMapping("/{id}/contents/{contentId}")
    public ResponseEntity<Course> deleteContentFromCourse(@PathVariable Long id, @PathVariable Long contentId) {
        System.out.println("deleteContentFromCourse");
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //get the content to delete
        Optional<Content> contentToDelete = contentService.getContentById(contentId);
        if (contentToDelete.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //remove the content from the course
        course.get().getContents().remove(contentToDelete.get());
        //delete the content
        contentService.deleteContent(contentId);

        return ResponseEntity.ok(course.get());
    }
}

