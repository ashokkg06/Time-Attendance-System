package com.wipro;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name = "admin", scheme="bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TimeAttendanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeAttendanceSystemApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
