package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//	@GetMapping("api/v1/student")
//	public List<Student> getStudent() {
//		return List.of(
//					new Student("Jonh Doe",
//							"jonhdoe@gmai.com",
//							LocalDate.of(2000,1,2),
//							23)
//				);
//	}
}
