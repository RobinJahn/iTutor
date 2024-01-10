package com.example.itutor.service;

import com.example.itutor.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseServiceI {

    Page<Course> getAllCourses(Pageable pageable);
    Optional<Course> getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Long id, Course updatedCourse);
    boolean deleteCourse(Long id);
}
