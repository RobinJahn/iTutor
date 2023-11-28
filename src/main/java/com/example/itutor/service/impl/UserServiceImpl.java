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

	// All Override methods for the student
	@Override
	public List<User> getAllStudents() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveStudent(User student) {
		// TODO Auto-generated method stub
		return userRepository.save(student);
	}

	@Override
	public User getStudentById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public User updateStudent(User student) {
		// TODO Auto-generated method stub
		return userRepository.save(student);
	}

	@Override
	public void delete(User student) {
		// TODO Auto-generated method stub
		userRepository.delete(student);
	}

	// All Override methods for the researcher
	@Override
	public List<User> getAllResearchers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveResearcher(User researcher) {
		// TODO Auto-generated method stub
		return userRepository.save(researcher);
	}

	@Override
	public User getResearcherById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public User updateResearcher(User researcher) {
		// TODO Auto-generated method stub
		return userRepository.save(researcher);
	}

	@Override
	public void deleteResearcher(User researcher) {
		// TODO Auto-generated method stub
		userRepository.delete(researcher);
	}

}
