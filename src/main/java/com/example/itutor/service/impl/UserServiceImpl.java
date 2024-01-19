package com.example.itutor.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.itutor.domain.Status;
import com.example.itutor.domain.User;
import org.springframework.stereotype.Service;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

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
		// TODO Auto-generated method stub
		user.setStatus(Status.ONLINE);
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
