package com.example.itutor.service.impl;

import java.util.List;

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
	public User saveUser(User student) {
		// TODO Auto-generated method stub
		return userRepository.save(student);
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(User student) {
		// TODO Auto-generated method stub
		return userRepository.save(student);
	}

	@Override
	public void delete(User student) {
		// TODO Auto-generated method stub
		userRepository.delete(student);
	}

}
