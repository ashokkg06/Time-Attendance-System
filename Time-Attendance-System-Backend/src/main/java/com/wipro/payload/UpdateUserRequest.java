package com.wipro.payload;

import java.time.LocalDate;

import com.wipro.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	private String name;
    private LocalDate dob;
    private String address;
    private Long mobile;
}
