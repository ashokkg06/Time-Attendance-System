package com.wipro.payload;

import java.time.LocalDate;
import java.util.List;

import com.wipro.entity.Attendance;
import com.wipro.entity.Role;

import lombok.Data;

@Data
public class UserResponse {
	private Long id;
	private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate dob;
    private String address;
    private String designation;
    private LocalDate hireDate;
    private Role role;
    private Long mobile;
    private Long managerId;
    private boolean checkedIn;
    private String token;
    private List<Attendance> attendances;
}
