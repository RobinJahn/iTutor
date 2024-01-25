package com.example.itutor.repository;

import com.example.itutor.domain.Answer;
import com.example.itutor.domain.Question;
import com.example.itutor.domain.Student;
import com.example.itutor.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByStudentAndQuestion(Student student, Question question);

}
