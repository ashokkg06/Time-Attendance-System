package com.wipro.payload;

import java.time.LocalDate;

import com.wipro.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate dob;
    private Role role;
    private Long managerId;
    private String designation;
    private LocalDate hireDate;
}