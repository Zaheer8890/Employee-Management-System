package com.angular.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angular.crud.DTO.StudentDto;
import com.angular.crud.entity.Student;
import com.angular.crud.repository.StudentRepo;




@Service
public class StudentService {

	@Autowired
    private StudentRepo studentRepository;

    
    //public List<Student> getAllStudents() {
        //return studentRepository.findAll();
   // }
    
    /*public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }*/
    
	/*this is use specifcation class and method filterByFields static method of class
    public Page<Student> searchStudents(String id ,String name, String dept, Integer age, Pageable pageable) {
        Specification<Student> spec = StudentSpecification.filterByFields(id ,name, dept, age);
        return studentRepository.findAll(spec, pageable);
    }*/
	
	// using @procedure call directly without stored procedure mapping
	@Transactional
	public Page<Student> searchStudents(String id, String name, String dept, Integer age, Pageable pageable) {
	    List<Student> allFiltered = studentRepository.fetchFiltered(id, name, age, dept);

	    int start = (int) pageable.getOffset();
	    int end = Math.min((start + pageable.getPageSize()), allFiltered.size());

	    List<Student> paged = allFiltered.subList(start, end);

	    return new PageImpl<>(paged, pageable, allFiltered.size());
	}
	
	
	//using Dto for partial object 
	
	/*
	public Page<StudentDto> searchStudents(String name , Pageable peagable){
		
		List<Object[]> results = studentRepository.fetchfiltered(name);
		
		List<StudentDto> dtoList = results.stream()
				                   .map(r -> new StudentDto((String) r[0] , (String) r[1] ))
				                   .collect(Collectors.toList());
		
		int start = (int) peagable.getOffset();
		int end = Math.min(start + peagable.getPageSize(), dtoList.size());
		
		 List<StudentDto> pagedList = dtoList.subList(start, end);

		 return new PageImpl<>(pagedList, peagable, dtoList.size());
		
		
	}*/
	
 



    //this is use query
    /*public Page<Student> getFilteredStudents(String id, String name, String dept, Integer age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByFilter(id, name, dept, age, pageable);
    }*/
    
    
    
    
  
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    
    public Student updateStudent(String id, Student updatedStudent) {
        if (studentRepository.existsById(id)) {
            updatedStudent.setId(id); 
            return studentRepository.save(updatedStudent);
        } else {
            throw new RuntimeException("Student with ID " + id + " not found."); 
        }
    }

    
    public void deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student with ID " + id + " not found.");
        }
    }
}
