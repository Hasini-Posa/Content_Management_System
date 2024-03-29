package com.example.cms.exception;

@SuppressWarnings("serial")
public class TopicNotSpecifiedException extends RuntimeException{

	private String message;

	public TopicNotSpecifiedException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}




}
