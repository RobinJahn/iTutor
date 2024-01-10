package com.example.itutor.service;

import com.example.itutor.domain.Student;

import java.util.List;

public interface StudentServiceI {
    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student studentDetails);

    void deleteStudent(Long id);
}
