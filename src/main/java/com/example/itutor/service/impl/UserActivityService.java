package com.example.itutor.service.impl;

import com.example.itutor.domain.UserActivity;
import com.example.itutor.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Autowired
    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }


    public List<UserActivity> getUserActivitiesByUsername(String username) {
        return userActivityRepository.findByUsername(username);
    }


    // Old approach
    public Map<String, Long> getUserActivityCountsByType(String type) {
        List<UserActivity> activities = userActivityRepository.findByActivityType(type);
        return activities.stream()
                .collect(Collectors.groupingBy(UserActivity::getUsername, Collectors.counting()));
    }

    public Map<LocalDate, Map<String, Long>> getCourseCreationStats() {
        List<UserActivity> activities = userActivityRepository.findByActivityType("Course Creation");
        System.out.println("Service: " + activities);

        return activities.stream()
                .collect(Collectors.groupingBy(
                        UserActivity::getDate,
                        Collectors.groupingBy(
                                UserActivity::getUsername,
                                Collectors.counting()
                        )
                ));
    }


    // Add other methods as needed

}

