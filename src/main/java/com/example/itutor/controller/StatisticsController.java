package com.example.itutor.controller;

import com.example.itutor.service.impl.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.*;

@Controller
public class StatisticsController {

    @Autowired
    private UserActivityService userActivityService;

    public StatisticsController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @RequestMapping(value = "/statistics/general", method = RequestMethod.GET)
    public String showStatistics(HttpServletRequest request, Model model) {

        return "statistics/general";
    }


    @RequestMapping("/statistics/userActivityCourseCreation")
    public String showCourseCreationStats(Model model) {
        Map<LocalDate, Map<String, Long>> courseCreationStats = userActivityService.getCourseCreationStats();
        // System.out.println("Controller: " + courseCreationStats);
        model.addAttribute("courseCreationStats", courseCreationStats);
        return "statistics/userActivityCourseCreation";
    }

    @RequestMapping("/statistics/courseEngagement")
    public String showCourseViewingStats(Model model) {
        Map<LocalDate, Map<String, Long>> courseViewingStats = userActivityService.getCourseViewingStats();
        // System.out.println("Controller: " + courseViewingStats);
        model.addAttribute("courseViewingStats", courseViewingStats);
        return "statistics/courseEngagement";
    }

    @RequestMapping("/statistics/contentEngagement")
    public String showContentViewingStats(Model model) {
        Map<LocalDate, Map<String, Long>> contentViewingStats = userActivityService.getContentViewingStats();
        // System.out.println("Controller: " + contentViewingStats);
        model.addAttribute("contentViewingStats", contentViewingStats);
        return "statistics/contentEngagement";
    }

}
