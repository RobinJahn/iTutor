package com.example.itutor.controller;

import com.example.itutor.domain.Student;
import com.example.itutor.service.impl.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private OpenAIService openAIService;

    @RequestMapping(value = "/")
    public String showHome() {
        return "/home";
    }

    @RequestMapping(value = "/test")
    public String showTest(Model model) {
        Student student = new Student();
        student.setId((long) -1);
        model.addAttribute("student", student);
        return "test/test";
    }

    @RequestMapping(value = "/layout")
    public String showLayout() {
        return "/layout";
    }
}
