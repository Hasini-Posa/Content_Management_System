package com.example.cms.exception;

public class InvalidPanelExceptiion extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public InvalidPanelExceptiion(String message) {
		super();
		this.message = message;
	}

	
	
}
