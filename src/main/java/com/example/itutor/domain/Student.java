package com.example.itutor.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class Student extends User{

    //Attributes
    private LocalDate birthDate;

    //Getters + Setters
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
