package com.example.itutor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name="users")
public class User {
    //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank
    String firstName=null;

    @NotBlank
    String lastName=null;

    @NotBlank(message = "{student.email.not.blank}")
    String email;

    @NotBlank
    String username;

    @NotBlank
    String password;

    //Getters + Setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "User {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username=" + username + ", password=" + password + '}';
    }

}
