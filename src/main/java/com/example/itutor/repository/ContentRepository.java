package com.example.itutor.repository;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
