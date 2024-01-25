package com.example.itutor.service.impl;

import com.example.itutor.domain.Answer;
import com.example.itutor.domain.Question;
import com.example.itutor.domain.Student;
import com.example.itutor.repository.StudentAnswerRepository;
import com.example.itutor.service.AnswerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerServiceI {

    @Autowired
    private StudentAnswerRepository answerRepository;
    @Override
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAnswersByStudentAndQuestion(Student student, Question question) {
        return answerRepository.findByStudentAndQuestion(student, question);
    }

}
