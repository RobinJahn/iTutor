package com.example.itutor.domain;

import jakarta.persistence.*;
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

    public UserActivity(LocalDate now, String activityType, String loggedInUserUsername) {
        this.date = now;
        this.activityType = activityType;
        this.username = loggedInUserUsername;
    }
}
