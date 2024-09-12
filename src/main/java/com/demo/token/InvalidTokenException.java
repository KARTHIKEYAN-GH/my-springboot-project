package com.demo.token;

public class InvalidTokenException extends RuntimeException {
	
	public InvalidTokenException(String message) {
		super(message);
	}
}
