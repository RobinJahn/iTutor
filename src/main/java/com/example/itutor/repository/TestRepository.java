package com.example.itutor.repository;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByCreatedBy(Expert expert);
}
