package com.example.itutor.service.impl;

import com.example.itutor.domain.Course;
import com.example.itutor.repository.CourseRepository;
import com.example.itutor.service.CourseServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseServiceI {
    private final CourseRepository courseRepository;
    //@Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            // Update the course fields
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());

            // Handle the contents list
            course.setContents(updatedCourse.getContents());

            // Save the updated course
            return courseRepository.save(course);
        }).orElse(null); // Or appropriate error handling
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false; // Or Error-Handling
    }
}
