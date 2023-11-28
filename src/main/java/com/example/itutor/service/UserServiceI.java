package com.example.itutor.service;

import com.example.itutor.domain.User;

import java.util.List;


public interface UserServiceI {
	
	List<User> getAllStudents();

	User saveStudent(User student);

	User getStudentById(Long id);

	User updateStudent(User student);
	
	void delete(User student);


}
