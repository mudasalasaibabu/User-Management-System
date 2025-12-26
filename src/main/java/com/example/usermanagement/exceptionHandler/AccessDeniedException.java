package com.example.usermanagement.exceptionHandler;

public class AccessDeniedException  extends RuntimeException{
	
	public AccessDeniedException(String msg) {
		super(msg);
	}
}
