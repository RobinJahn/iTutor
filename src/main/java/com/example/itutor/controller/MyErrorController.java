package com.example.itutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController{

    @RequestMapping("/error")
    public String handleError() {
        // Provide the path to your error page template
        return "error";
    }

}
