package com.example.itutor.controller;

import com.example.itutor.domain.Question;
import com.example.itutor.domain.Test;
import com.example.itutor.service.QuestionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionServiceI questionService;

    @GetMapping("/create")
    public String createFrageForm(Model model) {
        model.addAttribute("question", new Question());
        return "createQuestion";
    }

    @PostMapping("/create")
    public String createFrage(@ModelAttribute Question question) {
        questionService.createQuestion(question);
        // Annahme: frage.getTestId() gibt die ID des zugehörigen Tests zurück
        return "redirect:/questions/showByTest/" + question.getTest().getTestId();
    }

    @GetMapping("/showByTest/{testId}")
    public String showFragenByTestId(@PathVariable Long testId, Model model) {
        Test test = new Test();
        test.setTestId(testId);
        List<Question> questions = questionService.getQuestionByTest(test);
        model.addAttribute("question", questions);
        return "question";
    }

}
