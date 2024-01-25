package com.example.itutor.service;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Test;

import java.util.List;

public interface TestServiceI {
    Test createTest(Test test);
    List<Test> getTestsCreatedByExpert(Expert expert);
    List<Test> getAllTests();
}
