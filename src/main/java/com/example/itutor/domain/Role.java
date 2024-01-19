package com.example.itutor.domain;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        //Check if array is initialized
        if (this.users == null) {
            this.users = new java.util.ArrayList<User>();
        }
    	return this.users.add(user);
    }
}
