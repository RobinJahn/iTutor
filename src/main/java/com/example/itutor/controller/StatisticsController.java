package com.example.itutor.controller;

import com.example.itutor.domain.Holiday;
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
}
