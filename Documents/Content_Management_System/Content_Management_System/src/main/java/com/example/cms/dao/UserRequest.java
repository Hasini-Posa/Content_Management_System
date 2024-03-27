package com.example.cms.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserRequest {

	private String userName;
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	@Pattern(regexp = "^(?=.[0-9])(?=.[A-Z])(?=.[a-z])(?=.[!@#$%^&+=]).{8,}$",message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character")
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
