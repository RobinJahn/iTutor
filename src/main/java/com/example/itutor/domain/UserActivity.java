package com.example.itutor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userActivityId;
    private LocalDate date;
    private String activityType;
    private String username;
    private long courseId;

    public UserActivity(LocalDate now, String activityType, String loggedInUserUsername, Long courseId) {
        this.date = now;
        this.activityType = activityType;
        this.username = loggedInUserUsername;
        this.courseId = courseId;
    }
}
