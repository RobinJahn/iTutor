package com.example.itutor.repository;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Page<Content> findByCourseCourseId(Long courseId, Pageable pageable);
}
