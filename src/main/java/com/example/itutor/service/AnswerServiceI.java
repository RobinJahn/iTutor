package com.example.itutor.service;

import com.example.itutor.domain.Answer;
import com.example.itutor.domain.Question;
import com.example.itutor.domain.Student;

import java.util.List;


public interface AnswerServiceI {
    Answer saveAnswer(Answer answer);
    List<Answer> getAnswersByStudentAndQuestion(Student student, Question question);

}
