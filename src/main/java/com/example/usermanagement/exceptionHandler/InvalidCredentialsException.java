package com.example.usermanagement.exceptionHandler;

public class InvalidCredentialsException extends RuntimeException{
	public InvalidCredentialsException(String msg) {
		super(msg);
	}
}
