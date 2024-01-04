package com.example.itutor.service.impl;

import java.util.List;

import com.example.itutor.domain.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

	public class CustomUserCreationException extends RuntimeException {
		public CustomUserCreationException(String message) {
			super(message);
		}
		// You can add more constructors or methods as needed
	}

	//Why not autowired ??? - because constructor injection is better
	private UserRepositoryI userRepository;
	
	public UserServiceImpl(UserRepositoryI userRepository) {
		super();
		this.userRepository = userRepository;
	} 

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		try {
			// Code to save user
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			// Handle the exception, e.g., log the error and/or throw a custom exception
			// You can also provide a more user-friendly message or take other appropriate actions
			throw new CustomUserCreationException("Username already exists: " + user.getUsername());
		}
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userRepository.delete(user);
	}
}
