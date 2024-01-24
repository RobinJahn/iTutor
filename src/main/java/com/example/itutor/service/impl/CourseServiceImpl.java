package com.example.itutor.service.impl;

import com.example.itutor.config.MyUserDetails;
import com.example.itutor.domain.Course;
import com.example.itutor.domain.UserActivity;
import com.example.itutor.repository.CourseRepository;
import com.example.itutor.repository.UserActivityRepository;
import com.example.itutor.service.CourseServiceI;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseServiceI {
    private final CourseRepository courseRepository;
    private final UserActivityRepository userActivityRepository;
    //@Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserActivityRepository userActivityRepository) {
        this.courseRepository = courseRepository;
        this.userActivityRepository = userActivityRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course createCourse(Course course) {
        // Course has to be saved first, to get an ID
        courseRepository.save(course);

        String loggedInUserUsername = getLoggedInUsername();
        UserActivity activity = new UserActivity(LocalDate.now(), "Course Creation", loggedInUserUsername);
        userActivityRepository.save(activity);
        return course;
    }

    @Override
    public Course updateCourse(Long id, Course updatedCourse) {
        if (courseRepository.existsById(id)) {
            updatedCourse.setCourseId(id);
            return courseRepository.save(updatedCourse);
        }
        return null; // Or Error-Handling
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false; // Or Error-Handling
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
}
