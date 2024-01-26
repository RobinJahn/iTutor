package com.example.itutor.service.impl;

import com.example.itutor.domain.Course;
import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Question;
import com.example.itutor.domain.Test;
import com.example.itutor.repository.TestRepository;
import com.example.itutor.service.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestServiceI {
    @Autowired
    private TestRepository testRepository;

    @Override
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public List<Test> getTestsCreatedByExpert(Expert expert) {
        return testRepository.findByCreatedBy(expert);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
    @Override
    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    public Test updateTest(Long id, Test updatedTest) {
        if (testRepository.existsById(id)) {
            updatedTest.setTestId(id);
            return testRepository.save(updatedTest);
        }
        return null; // Or Error-Handling
    }
}
