package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
	
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args->{
			Student jonhdoe = 	new Student("Jonh Doe",
					"jonhdoe@gmai.com",
					LocalDate.of(2000,1,2));
			Student dara = 	new Student("Dara",
					"dara@gmai.com",
					LocalDate.of(2000,1,2));
			repository.saveAll(List.of(jonhdoe,dara));
		};
		
	}
}
