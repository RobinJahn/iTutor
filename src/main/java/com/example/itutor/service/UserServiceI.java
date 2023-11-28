package com.example.itutor.service;

import com.example.itutor.domain.User;

import java.util.List;


public interface UserServiceI {
	
	List<User> getAllStudents();

	User saveStudent(User student);

	User getStudentById(Long id);

	User updateStudent(User student);

	// should be named deleteStudent???
	void delete(User student);

	// UserService for the researcher User
	List<User> getAllResearchers();

	User saveResearcher(User researcher);

	User getResearcherById(Long id);

	User updateResearcher(User researcher);

	// is one delete function for each user not enough?
	void deleteResearcher(User researcher);


}
