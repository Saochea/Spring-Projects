package com.bbc.restcrudoperation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bbc.restcrudoperation.model.User;
import com.bbc.restcrudoperation.service.ApiResponse;
import com.bbc.restcrudoperation.service.UserService;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService,PasswordEncoder passwordEncoder) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	// ============ create user =================
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user){
		
		String firstName = user.getFistName()!=null?user.getFistName().trim():"";
		String lastName = user.getLastName()!=null?user.getLastName().trim():"";
		
		// encode password user
		String encodedPassword = passwordEncoder.encode(user.getPassword());
	        	user.setPassword(encodedPassword);

		if(firstName!="" && lastName!="") {
			userService.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","user created"));
		}
		
		return ResponseEntity.ok(new ApiResponse("require", "First/Last name is require."));
	}
	
	// update user end point
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody User user){
		
		if(user.getFistName().trim()=="" || user.getLastName().trim()=="") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("require", "First/Last name is require."));
		}
		
		try {
			// encode password user
			String encodedPassword = passwordEncoder.encode(user.getPassword());
		        	user.setPassword(encodedPassword);

			userService.updateUser(id, user);
			return ResponseEntity.ok(new ApiResponse("success","user updated."));
			
		}catch (IllegalArgumentException e) {
			
			return ResponseEntity.ok(new ApiResponse("failed",e.getMessage()));
			
		}
	}
	
	// find all users 
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	// get user by id
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		
		User user = userService.getUserById(id);
		
		if(user!=null) {
			return ResponseEntity.ok(user);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed","user with given id doesn't exist."));
	}
	
	// delete user by id 
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		
		User isExist = userService.getUserById(id);
		
		if(isExist!=null) {
			userService.deleteUserById(id);
			return ResponseEntity.status(HttpStatus.GONE).body(new ApiResponse("success","User deleted."));
		}
		
		return ResponseEntity.ok(new ApiResponse("failed","user not found"));
	}
	
	
}
