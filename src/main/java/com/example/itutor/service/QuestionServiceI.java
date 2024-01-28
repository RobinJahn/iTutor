package com.example.itutor.service;

import com.example.itutor.domain.Question;
import com.example.itutor.domain.Test;

import java.util.List;

public interface QuestionServiceI {

    Question createQuestion(Question question);
    List<Question> getQuestionByTest(Test test);
}
