package com.example.itutor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.itutor.domain.Student;
import com.example.itutor.repository.StudentRepositoryI;
import com.example.itutor.repository.impl.StudentRepositoryImp;
import com.example.itutor.service.StudentServiceI;

@Service
public class StudentServiceImpl implements StudentServiceI{

	//Why not autowired ??? - because constructor injection is better
	private StudentRepositoryI studentRepository;
	
	public StudentServiceImpl(StudentRepositoryI studentRepository) {
		super();
		this.studentRepository = studentRepository;
	} 
	
	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return (List<Student>) studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}

	@Override
	public void delete(Student student) {
		// TODO Auto-generated method stub
		studentRepository.delete(student);	
	}

}
