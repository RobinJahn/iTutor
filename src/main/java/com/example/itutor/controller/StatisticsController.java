package com.example.itutor.controller;

import com.example.itutor.domain.Holiday;
import com.example.itutor.service.HolidayServiceI;
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
    final HolidayServiceI holidayService;

    @Autowired
    private UserActivityService userActivityService;

    public StatisticsController(HolidayServiceI holidayService, UserActivityService userActivityService) {
        this.holidayService = holidayService;
        this.userActivityService = userActivityService;
    }

    @RequestMapping("/statistics/next-holiday")
    public String showNextHoliday(Model model) {
        List<Holiday> holidays = holidayService.getHolidays("DE");
        LocalDate today = LocalDate.now();

        Optional<Holiday> nextHoliday = holidays.stream()
                .filter(holiday -> holiday.getDate().isAfter(today))
                .min(Comparator.comparing(Holiday::getDate));

        if (nextHoliday.isPresent()) {
            model.addAttribute("holiday", nextHoliday.get());
        } else {
            model.addAttribute("message", "Keine weiteren Feiertage in diesem Jahr gefunden.");
        }

        return "statistics/next-holiday";
    }

    @RequestMapping(value = "/statistics/general", method = RequestMethod.GET)
    public String showStatistics(HttpServletRequest request, Model model) {

        return "statistics/general";
    }


    @RequestMapping("/statistics/userActivityCourseCreation")
    public String showCourseCreationStats(Model model) {
        Map<LocalDate, Map<String, Long>> courseCreationStats = userActivityService.getCourseCreationStats();
        System.out.println("Controller: " + courseCreationStats);
        model.addAttribute("courseCreationStats", courseCreationStats);
        return "statistics/userActivityCourseCreation";
    }

}
