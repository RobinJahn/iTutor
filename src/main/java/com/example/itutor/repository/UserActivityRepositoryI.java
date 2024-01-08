package com.example.itutor.repository;

import com.example.itutor.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityRepositoryI extends JpaRepository<UserActivity, Long> {
    List<UserActivity> findByActivityType(String courseCreation);
}
