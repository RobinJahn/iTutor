package com.example.itutor.service.impl;

import com.example.itutor.config.MyUserDetails;
import com.example.itutor.domain.Role;
import com.example.itutor.domain.User;
import com.example.itutor.repository.UserRepositoryI;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryI userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDefaultUser() {
        userRepository.findByUsername("defaultUser").orElseGet(() -> {
            User defaultUser = new User();
            defaultUser.setUsername("defaultUser");
            defaultUser.setPassword(passwordEncoder.encode("defaultPassword"));
            defaultUser.setEmail("default@example.com"); // Set a default email
            defaultUser.setFirstName("Default"); // Set a default first name
            defaultUser.setLastName("User"); // Set a default last name
            return userRepository.save(defaultUser);
        });
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new MyUserDetails(user);
    }


}


