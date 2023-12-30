package com.example.itutor.repository;


import com.example.itutor.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryI extends MyBaseRepository<User, Long>{
    Optional<User> findByUsername(String username);
    List<User> findAll();
}
