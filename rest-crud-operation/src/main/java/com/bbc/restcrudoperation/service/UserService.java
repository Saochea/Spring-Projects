package com.bbc.restcrudoperation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bbc.restcrudoperation.model.User;
import com.bbc.restcrudoperation.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// get all users 
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	// find one
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	// delete by id
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
	
	// create user service
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	// update user service
	public User updateUser(Long id,User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		if(existingUser!=null) {
			existingUser.setFirstName(user.getFistName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
		}
		
		return user;
	}
	
	// delete user service
	public void deleteUser(Long id) {
		User existingUser = userRepository.findById(id).orElse(null);
		if(existingUser!=null){
			userRepository.deleteById(id);
		}
		
		throw new IllegalArgumentException("user not found.");
	}
	
}
