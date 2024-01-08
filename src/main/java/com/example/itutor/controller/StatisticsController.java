package com.example.itutor.controller;

import com.example.itutor.domain.Holiday;
import com.example.itutor.domain.UserActivity;
import com.example.itutor.repository.UserActivityRepositoryI;
import com.example.itutor.service.HolidayServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class StatisticsController {

    @Autowired
    final HolidayServiceI holidayService;

    @Autowired
    private UserActivityRepositoryI userActivityRepositoryI;

    public StatisticsController(HolidayServiceI holidayService) {
        this.holidayService = holidayService;
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

    // Methode für detaillierte Statistik 1
    @RequestMapping("/statistics/detail1")
    public String showDetailedStatistics1(Model model) {
        // something
        return "statistics/details1";
    }

    // Methode für detaillierte Statistik 2
    @RequestMapping("/statistics/detail2")
    public String showDetailedStatistics2(Model model) {
        // something
        return "statistics/details2";
    }

    @RequestMapping(value = "/statistics/statistics", method = RequestMethod.GET)
    public String showStatisticsPage() {
        return "statistics/statistics";
    }

    @RequestMapping("/statistics/userActivityCourseCreation")
    public String showCourseCreationStats(Model model){
        List<UserActivity> courseCreationActivities = userActivityRepositoryI.findByActivityType("Course Creation");
        System.out.println("the UserActivity looks like this: " + courseCreationActivities);
        model.addAttribute("courseCreations", courseCreationActivities);
        return "statistics/userActivityCourseCreation";
    }
}
