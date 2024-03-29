package com.example.cms.exception;

@SuppressWarnings("serial")
public class BlogNotFoundException extends RuntimeException{

	private String message;

	public BlogNotFoundException(String message) {
		
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
