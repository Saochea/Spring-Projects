package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
	
	private final StudentRepository studentRepository ;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository  = studentRepository;
	}
	
	public List<Student> getStudent() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		// TODO Auto-generated method stub
		Optional<Student> studentByEmail =  studentRepository.findStudentByEmail(student.getEmail());
		//System.out.println(student);
		
		if(studentByEmail.isPresent()) {
			throw new IllegalStateException("Email taken");
		}
		studentRepository.save(student);
		
		
	}


	public void deleteStudent(Long studentId) {
		// TODO Auto-generated method stub
		boolean exists = studentRepository.existsById(studentId);
		if(!exists) {
			throw new IllegalStateException("student with id "+studentId+" doesn't exist.");
		}
		studentRepository.deleteById(studentId);
	}
	
	@Transactional
	public void updateStudent(Long studentId, String name, String email) {
		// TODO Auto-generated method stub
		Student student = studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException(
					"student id"+studentId+" does not exists."
				));
		
		if(name!=null && name.length()>0) {
			student.setName(name);
		}
		
		if(email!=null && email.length()>0) {
			student.setEmail(email);
		}
	}
}
