package com.example.itutor.domain;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="students")
public class Student extends User implements Serializable {

    //Attributes
    private LocalDate birthDate;

    //Getters + Setters
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Student {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", birthDate=" + birthDate + '}';
    }
}
