package com.example.cms.exception;

@SuppressWarnings("serial")
public class BlockAlreadyExistByTitleException extends RuntimeException{

	private String message;

	public BlockAlreadyExistByTitleException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
