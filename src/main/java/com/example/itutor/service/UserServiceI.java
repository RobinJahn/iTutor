package com.example.itutor.service;

import com.example.itutor.domain.User;

import java.util.List;


public interface UserServiceI {
	
	List<User> getAllUsers();

	User saveUser(User user);

	User getUserById(Long id);

	User updateUser(User user);
	
	void delete(User user);

	void disconnect(User user);

	List<User> findConnectedUsers();

}
