package com.example.itutor.controller;

import com.example.itutor.domain.Answer;
import com.example.itutor.domain.Question;
import com.example.itutor.service.AnswerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerServiceI answerService;
    @PostMapping("/save")
    public String saveAnswer(@ModelAttribute Answer answer) {
        answerService.saveAnswer(answer);
        // Annahme: antwort.getQuestionId() gibt die ID der zugehörigen Frage zurück
        return "redirect:/answers/showByQuestion/" + answer.getQuestion().getId();
    }

    @GetMapping("/showByQuestion/{questionId}")
    public String showAnswerByQuestion(@PathVariable Long questionId, Model model) {
        Question question = new Question();
        question.setId(questionId);
        // Annahme: Du möchtest die Antworten zu einer bestimmten Frage anzeigen
        List<Answer> answers = answerService.getAnswersByStudentAndQuestion(null, question);
        model.addAttribute("answers", answers);
        return "answers";
    }
}
