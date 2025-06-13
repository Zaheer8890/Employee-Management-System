package com.angular.crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.ParameterMode;



/*@NamedStoredProcedureQuery(
	    name = "Student.fetchDynamic",
	    procedureName = "fetchStudent",
	    resultClasses = Student.class,
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Id", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_name", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_age", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_dept", type = String.class)
	    }
	)*/
@Entity
public class Student {
 
	@Id
	private String id ;
	
	private String name ;
	
	private int age ;
	
	private String dept ;

	public String getId() {
		return id;
	}
	
	

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Student(String id, String name, int age, String dept) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.dept = dept;
	}



	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}



	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", dept=" + dept + "]";
	}
	
	
	
}
