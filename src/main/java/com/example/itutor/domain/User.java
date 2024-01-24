package com.example.itutor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;


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
    @Column(unique = true)
    String username;

    @NotBlank
    String password;

    private boolean active = true;

    // Used for Chat feature to see if a user is currently online
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="userrole",
            joinColumns = @JoinColumn(name="iduser"),
            inverseJoinColumns = @JoinColumn(name="idrole")
    )
    @JsonIgnore
    private List<Role> roles = new ArrayList<Role>();



    //Getters + Setters
    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
        for (Role role : roles) {
            role.addUser(this);
        }
    }

    public boolean addRole(Role role) {
        //Check if the roles array is null
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        //Check if the role is already in the list
        if (this.roles.contains(role)) {
            return false;
        }
        this.roles.add(role);
        return role.addUser(this);
    }



    @Override
    public String toString(){
        return "User {" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username=" + username + ", password=" + password + '}';
    }

}
