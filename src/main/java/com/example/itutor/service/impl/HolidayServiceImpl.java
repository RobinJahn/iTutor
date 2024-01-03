package com.example.itutor.service.impl;

import com.example.itutor.domain.Holiday;
import com.example.itutor.service.HolidayServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayServiceI {

    @Override
    public List<Holiday> getHolidays(String country) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://date.nager.at/Api/v2/PublicHolidays/2024/" + country;
        ResponseEntity<Holiday[]> response = restTemplate.getForEntity(url, Holiday[].class);
        return Arrays.asList(response.getBody());
    }
}

