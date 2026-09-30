package com.wipro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidCredentialsException extends IllegalArgumentException {
	public InvalidCredentialsException()
	{
		super("Invalid email or password combination");
	}
}