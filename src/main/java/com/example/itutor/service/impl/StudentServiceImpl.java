package com.example.itutor.service.impl;

import com.example.itutor.domain.Student;
import com.example.itutor.repository.StudentRepository;
import com.example.itutor.service.StudentServiceI;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceI {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id:" + id));
    }

    public Student createStudent(Student student) {
        // Erstellung eines Studenten
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        // Aktualisierung eines Studenten
        Student student = getStudentById(id);
        // Details für den Studenten setzen und speichern
        student.setUsername(studentDetails.getUsername());
        student.setEmail(studentDetails.getEmail());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setPassword(studentDetails.getPassword());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        // Löschen eines Studenten
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
