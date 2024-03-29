package com.example.cms.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.dao.UserRequest;
import com.example.cms.dto.UserRespose;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ResponseStructure<UserRespose> userStructure;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserRespose> userStructure,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userStructure = userStructure;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserRespose>> saveUser(UserRequest userRequest) {

		if(!userRepository.existsByEmail(userRequest.getEmail()))
			return ResponseEntity.ok(userStructure.setData(mapToUserResponse(userRepository.save(changeToUser(userRequest, new User())), new UserRespose())).setMessage("User REgister Success").setStatusCode(HttpStatus.OK.value()));

		throw new UserAlreadyExistByEmailException("Failed to Register User");
	}

	public User changeToUser(UserRequest userRequest,User user) {
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserName(userRequest.getUserName());
		return user;
	}

	public UserRespose mapToUserResponse(User user,UserRespose userResponse)
	{
		userResponse.setEmail(user.getEmail());
		userResponse.setUsername(user.getUserName());
		userResponse.setUserId(user.getUserId());

		return userResponse;
	}


	@Override
	public ResponseEntity<ResponseStructure<UserRespose>> findUserByUserId(int userId) {
		return userRepository.findById(userId).map(user -> {
			if(!user.isDeleted())
				return ResponseEntity.ok(userStructure.setMessage("Found Sucess").setStatusCode(HttpStatus.OK.value()).setData(mapToUserResponse(user, new UserRespose())));

			throw new UserNotFoundByIdException("User Id Already Deleted");
		}

				).orElseThrow( ()-> new UserNotFoundByIdException("User Not Found By Id"));


	}

	@Override
	public ResponseEntity<ResponseStructure<UserRespose>> softDeleteUserByUserId(int userId) {
		return userRepository.findById(userId).map(user -> {
			return ResponseEntity.ok(userStructure.setMessage("Data").setStatusCode(HttpStatus.OK.value()).setData(mapToUserResponse(userRepository.save(user.setDeleted(true)), new UserRespose())));

		}).orElseThrow( () -> new UserNotFoundByIdException("User Not Found By Id"));
	}

	

}
