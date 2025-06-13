package com.angular.crud.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.angular.crud.DTO.StudentDto;
import com.angular.crud.entity.Student;
import com.angular.crud.service.StudentService;

//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

@RestController
public class StudentController {

	@Autowired
    private StudentService studentService;

   
	/*@GetMapping("/showStudent")
	public ResponseEntity<?> showStudent() {
	//public ResponseEntity<?> showStudent(HttpServletRequest request) {
	   // HttpSession session = request.getSession(false); 

	    /*System.out.println("Checking session");

	    if (session == null) {
	        System.out.println("No session found");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No session found");
	    }

	    
	    System.out.println("Session exists! ID: " + session.getId());
	    System.out.println("Session User: " + session.getAttribute("user"));

	  
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        System.out.println(" User not authenticated!");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
	    }*/

	    
	   /* List<Student> students = studentService.getAllStudents();
	    return ResponseEntity.ok(students);
	}*/
	
	/*@GetMapping("/showStudent") // simple fetch all with pagination
	public ResponseEntity<Page<Student>> showStudent(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Student> studentPage = studentService.getAllStudents(pageable);
	    return ResponseEntity.ok(studentPage);
	}*/

 
	// uses jpa creria api and java class extend repo with JpaSpecificationExecutor<> and  and also use @namesd stored procedure & 
	   //@procedure without mapping
	@GetMapping("/showStudent")
	public ResponseEntity<Page<Student>> showStudent(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size,
	        @RequestParam(required = false) String id,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String dept,
	        @RequestParam(required = false) Integer age
	) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Student> studentPage = studentService.searchStudents(id ,name, dept, age, pageable);
	    return ResponseEntity.ok(studentPage);
	}
	
	/*
	 using dto for partial response via procedure using native query true
	@GetMapping("/showStudent")
	public ResponseEntity<Page<StudentDto>> filterStudents(
	        @RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<StudentDto> dtoPage = studentService.searchStudents(name, pageable);
	    return ResponseEntity.ok(dtoPage);
	}
*/


	//using custom query in repo interface
	/* @GetMapping("/showStudent")
	    public Page<Student> showStudent(
	            @RequestParam(required = false) String id,
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String dept,
	            @RequestParam(required = false) Integer age,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "8") int size) {

	        return studentService.getFilteredStudents(id, name, dept, age, page, size);
	    }*/
	
	
	
    
    @GetMapping("/showStudent/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PostMapping("/addStudent")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        System.out.println("Received Student: " + student); // Debugging
        Student savedStudent = studentService.addStudent(student);
        System.out.println("Saved Student: " + savedStudent); // Debugging
        return ResponseEntity.ok(savedStudent);
    }


    
    @PutMapping("updateStudent/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.updateStudent(id, updatedStudent);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
    @GetMapping("/deletestudent/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
    	System.out.println("Received Student: " );
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
}
