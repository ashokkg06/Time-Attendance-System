package com.wipro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends IllegalArgumentException {
	public UserNotFoundException(String name)
	{
		super("User with " + name + " Not found");
	}

	public UserNotFoundException(Long userId)
	{
		super("User " + userId + " Not found");
	}
}