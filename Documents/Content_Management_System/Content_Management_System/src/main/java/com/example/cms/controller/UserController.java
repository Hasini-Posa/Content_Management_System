package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.cms.dao.UserRequest;
import com.example.cms.dto.UserRespose;
import com.example.cms.model.User;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@RestController
@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserRespose>> saveUser(@RequestBody UserRequest user)
	{
	return userService.saveUser(user);	
	}
	
	@GetMapping("/test")
	public String test() {
		return "Hello from cms";
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserRespose>> findUserByUserId(@PathVariable int userId){
		return userService.findUserByUserId(userId);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserRespose>> deleteUser(@PathVariable int userId){
		return userService.softDeleteUserByUserId(userId);
	}

	
}
