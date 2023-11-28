package com.example.itutor.repository.impl;



import org.springframework.data.repository.CrudRepository;
import com.example.itutor.domain.Student;
import com.example.itutor.repository.StudentRepositoryI;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryImp extends StudentRepositoryI, CrudRepository<Student, Long>{

}
