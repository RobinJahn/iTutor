package com.example.itutor.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.itutor.domain.Status;
import com.example.itutor.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

	private UserRepositoryI userRepository;
	
	public UserServiceImpl(UserRepositoryI userRepository) {
		super();
		this.userRepository = userRepository;
	} 

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		try {
			user.setStatus(Status.ONLINE);
			return userRepository.save(user);
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public void disconnect(User user) {
		System.out.println("User to be disconnected: " + user);
		userRepository.findById(user.getId()).ifPresent(storedUser -> {
			storedUser.setStatus(Status.OFFLINE);
			userRepository.save(storedUser);
		});
	}

	@Override
	public List<User> findConnectedUsers() {
		return userRepository.findAllByStatus(Status.ONLINE);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public User getCurrentUser() {
		//get authentication and with that the current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = null;
		if(authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if(principal instanceof org.springframework.security.core.userdetails.UserDetails) {
				currentUserName = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
			}else {
				currentUserName = principal.toString();
			}
		}
		if(currentUserName == null) {
			return null;
		}
		return findByUsername(currentUserName);
	}
}
