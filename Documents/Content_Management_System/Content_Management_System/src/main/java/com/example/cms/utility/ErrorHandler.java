package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserAlreadyExistByEmailException;

@RestControllerAdvice
public class ErrorHandler {
	
	ErrorStructure<String> errorStrucutre;
	
	

	public ErrorHandler(ErrorStructure<String> errorStrucutre) {
		this.errorStrucutre = errorStrucutre;
	}



	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return new ResponseEntity<ErrorStructure<String>>(errorStrucutre.setMessage("Registration Not Success").setStatusCode(HttpStatus.BAD_REQUEST.value()).setData(ex.getMessage()),HttpStatus.BAD_REQUEST);
		
	}
}
