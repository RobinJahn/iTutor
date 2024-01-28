package com.example.itutor.repository;

import com.example.itutor.domain.Question;
import com.example.itutor.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTest(Test test);

}
