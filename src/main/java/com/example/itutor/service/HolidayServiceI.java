package com.example.itutor.service;

import com.example.itutor.domain.Holiday;

import java.util.List;

public interface HolidayServiceI {
    List<Holiday> getHolidays(String country);
}
