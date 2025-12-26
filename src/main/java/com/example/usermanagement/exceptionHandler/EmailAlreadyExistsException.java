package com.example.usermanagement.exceptionHandler;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException(String msg) {
		super(msg);
	}
}
