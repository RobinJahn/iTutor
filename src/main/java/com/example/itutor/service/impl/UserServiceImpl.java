package com.example.itutor.service.impl;

import java.util.List;

import com.example.itutor.domain.Status;
import com.example.itutor.domain.User;
import com.example.itutor.service.EmailServiceI;
import org.springframework.stereotype.Service;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

	//Why not autowired ??? - because constructor injection is better
	private UserRepositoryI userRepository;
	private EmailServiceI emailService;
	
	public UserServiceImpl(UserRepositoryI userRepository, EmailServiceI emailService) {
		super();
		this.userRepository = userRepository;
		this.emailService = emailService;
	} 

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setStatus(Status.ONLINE);
		// Sende Bestätigungs-E-Mail
		String confirmationSubject = "Confirmation Email";
		String confirmationText = "Thank you for signing up!";
		emailService.sendConfirmationEmail(user.getEmail(), confirmationSubject, confirmationText);
		return userRepository.save(user);
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
