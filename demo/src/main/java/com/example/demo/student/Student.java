package com.example.demo.student;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public class Student {
	@Id 
	@SequenceGenerator(name="student_sequence",sequenceName="student_sequence",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="student_sequence")
	
	private Long id;
	private String name;
	private String email;
	private LocalDate dob;
	
	@Transient
	private Integer age;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(Long id,String name,String email,LocalDate dob) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.email = email;
		this.dob = dob;
	}
	
	public Student(String name,String email,LocalDate dob) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.email = email;
		this.dob = dob;
		
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public Integer getAge() {
		
		return Period.between(this.dob, LocalDate.now()).getYears();
		
	}
	public LocalDate getDob() {
		return dob;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("ID: "+id+" Name: "+name+" email: "+email+" dob"+dob);
	}
	
}
