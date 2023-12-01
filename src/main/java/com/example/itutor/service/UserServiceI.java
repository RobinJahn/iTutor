package com.example.itutor.service;

import com.example.itutor.domain.User;

import java.util.List;


public interface UserServiceI {
	
	List<User> getAllUsers();

	User saveUser(User student);

	User getUserById(Long id);

	User updateUser(User student);
	
	void delete(User student);


}
