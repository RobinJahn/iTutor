package com.example.itutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

    @RequestMapping(value = "/home")
    public String showHome() {
        return "/home";
    }
}
