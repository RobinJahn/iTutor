package com.example.itutor.service;

import com.example.itutor.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseServiceI {

    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Long id, Course updatedCourse);
    boolean deleteCourse(Long id);
}
