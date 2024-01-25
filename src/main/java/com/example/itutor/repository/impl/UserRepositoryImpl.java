package com.example.itutor.repository.impl;



import org.springframework.data.repository.CrudRepository;
import com.example.itutor.domain.User;
import com.example.itutor.repository.UserRepositoryI;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends UserRepositoryI, CrudRepository<User, Long>{
}
