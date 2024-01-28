package com.example.itutor.config;

import com.example.itutor.domain.Role;
import com.example.itutor.domain.Status;
import com.example.itutor.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;


    private String userName;
    private String password;
    private boolean active;
    private Status status;
    private List<GrantedAuthority> authorities;

    private List<Role> roles;


    public MyUserDetails(User user) {

        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        System.out.println("password of the user is=" + password);
        System.out.println("userName of the user is=" + this.userName);
        System.out.println("the user: " + userName + " has the status: " + status);
        this.active = user.isActive();

        //getting authorities from the DB
        List<Role> myRoles = (List<Role>) user.getRoles();

        System.out.println("the user " + user.getUsername() + " has " +
                myRoles.size() + " roles");

        //Print role description
        for (int i = 0; i < myRoles.size(); i++) {
            System.out.println("the role " + i + " of the user " + user.getUsername() + " is " + myRoles.get(i).getDescription());
        }

        //authorities are required by Userdetails from Spring Security
        this.roles = myRoles;
        authorities = new ArrayList<>();

        //passing the authorities of each Profile from the DB to the Spring Security collection UserDetails.authorities
        for (int i = 0; i < myRoles.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(myRoles.get(i).getDescription().toUpperCase()));
            System.out.println("the authority" + i + " of the profile " + myRoles.get(i).getDescription() + " of the user " + user.getUsername() + " is " + myRoles.get(i).getDescription());
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

}
