package com.example.cms.exception;

@SuppressWarnings("serial")
public class PanelNotFoundByIdException extends RuntimeException{

	private String message;

	public PanelNotFoundByIdException(String message) {
		this.message = message;
	}
	
}
