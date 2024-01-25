package com.example.itutor.controller;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Test;
import com.example.itutor.service.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestServiceI testService;

    @GetMapping
    public String showAllTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "allTests";
    }

    @GetMapping("/create")
    public String createTestForm(Model model) {
        model.addAttribute("test", new Test());
        return "experts/createTest";
    }

    @PostMapping("/create")
    public String createTest(@ModelAttribute Test test) {
        testService.createTest(test);
        return "redirect:/allTests";
    }

    @GetMapping("/createdByExpert/{expertId}")
    public String showTestsCreatedByExpert(@PathVariable Long expertId, Model model) {
        Expert expert = new Expert();
        expert.setId(expertId);
        List<Test> tests = testService.getTestsCreatedByExpert(expert);
        model.addAttribute("tests", tests);
        return "allTests";
    }
}
