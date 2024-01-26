package com.example.itutor.controller;

import com.example.itutor.domain.*;
import com.example.itutor.service.QuestionServiceI;
import com.example.itutor.service.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestServiceI testService;
    @Autowired
    private QuestionServiceI questionService;


    @GetMapping
    public String showAllTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "tests/allTests";
    }

    @GetMapping("/create")
    public String createTestForm(Model model) {
        model.addAttribute("test", new Test());
        return "tests/allTests";
    }

    @PostMapping("/create")
    public String createTest(@ModelAttribute Test test) {
        testService.createTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/{id}")
    public String getTestById(@PathVariable Long id,
                                Model model) {
        Test test = testService.getTestById(id).orElse(null);
        if (test == null) {
            return "error";
        }

        List<Question> questions = questionService.getQuestionByTest(test);
        model.addAttribute("questions", questions);
        model.addAttribute("test", test);

        return "tests/test";
    }

    @GetMapping("/createdByExpert/{expertId}")
    public String showTestsCreatedByExpert(@PathVariable Long expertId, Model model) {
        Expert expert = new Expert();
        expert.setId(expertId);
        List<Test> tests = testService.getTestsCreatedByExpert(expert);
        model.addAttribute("tests", tests);
        return "tests/allTests";
    }

    @PostMapping("/addQuestion")
    public String addQuestionTest(@RequestParam Long testId, @RequestParam String question, RedirectAttributes redirectAttributes) {
        // Retrieve the test by ID
        Optional<Test> optionalTest = testService.getTestById(testId);

        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();

            // Create a new question object
            Question newQuestion = new Question();
            newQuestion.setTest(test);
            newQuestion.setQuestion(question);

            //Save question in the database
            questionService.createQuestion(newQuestion);

            // Add the new question to the test
            test.addQuestion(newQuestion);

            // Update the test with the new question
            testService.updateTest(testId, test);

            redirectAttributes.addFlashAttribute("success", "Question added successfully!");
            return "redirect:/tests/" + testId;
        } else {
            redirectAttributes.addFlashAttribute("error", "Test not found!");
            return "redirect:/tests";
        }
    }
}
