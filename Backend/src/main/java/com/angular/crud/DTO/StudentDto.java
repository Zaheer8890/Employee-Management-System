package com.angular.crud.DTO;

public class StudentDto {

	String name;
	String dept;
	
	
	
	public StudentDto(String name, String dept) {
		super();
		this.name = name;
		this.dept = dept;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
}
