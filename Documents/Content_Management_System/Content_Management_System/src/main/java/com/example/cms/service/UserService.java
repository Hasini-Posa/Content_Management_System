package com.example.cms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cms.dao.UserRequest;
import com.example.cms.dto.UserRespose;
import com.example.cms.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserRespose>> saveUser(UserRequest user);

	ResponseEntity<ResponseStructure<UserRespose>> findUserByUserId(int userId);

	ResponseEntity<ResponseStructure<UserRespose>> softDeleteUserByUserId(int userId);
	
	
	
}
