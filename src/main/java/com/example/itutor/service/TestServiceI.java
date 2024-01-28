package com.example.itutor.service;

import com.example.itutor.domain.Course;
import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Test;

import java.util.List;
import java.util.Optional;

public interface TestServiceI {
    Test createTest(Test test);
    List<Test> getTestsCreatedByExpert(Expert expert);
    List<Test> getAllTests();
    Optional<Test> getTestById(Long id);

    Test updateTest(Long id, Test updatedTest);
}
