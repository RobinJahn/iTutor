package com.example.itutor.service.impl;

import com.example.itutor.domain.Content;
import com.example.itutor.domain.Question;
import com.example.itutor.domain.Test;
import com.example.itutor.repository.QuestionRepository;
import com.example.itutor.service.QuestionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionServiceI {
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestionByTest(Test test) {
        return questionRepository.findByTest(test);
    }

}
