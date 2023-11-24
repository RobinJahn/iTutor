package com.example.itutor.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class Student {
    long id;
    @NotBlank
    String firstName=null;
    @NotBlank
    String lastName=null;
    @NotBlank(message = "{student.email.not.blank}")
    String email;

    private LocalDate birthDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
