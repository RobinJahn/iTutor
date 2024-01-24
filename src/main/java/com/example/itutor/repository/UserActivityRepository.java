package com.example.itutor.repository;

import com.example.itutor.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findByUsername(String username);
    List<UserActivity> findByActivityType(String activityType);
}
