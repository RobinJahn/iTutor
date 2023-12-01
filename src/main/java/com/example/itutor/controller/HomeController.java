package com.example.itutor.controller;

import com.example.itutor.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/home")
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

    @RequestMapping(value = "/preselectUser")
    public String showPreselectUser() {
        return "/preselectUser";
    }
}
