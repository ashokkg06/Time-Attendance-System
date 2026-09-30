package com.wipro.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {
	public UserAlreadyExistsException(String username) {
		super("User with username: " + username + " Already Exists");
	}
}