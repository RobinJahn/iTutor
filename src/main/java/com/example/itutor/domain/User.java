package com.example.itutor.domain;

import jakarta.validation.constraints.NotBlank;

public abstract class User {
    //Attributes
    long id;

    @NotBlank
    String firstName=null;

    @NotBlank
    String lastName=null;

    @NotBlank(message = "{student.email.not.blank}")
    String email;

    //Getters + Setters
    public abstract long getId();

    public abstract void setId(long id);

    public abstract String getFirstName();

    public abstract void setFirstName(String firstName);

    public abstract String getLastName();

    public abstract void setLastName(String lastName);

    public abstract String getEmail();

    public abstract void setEmail(String email);


}
