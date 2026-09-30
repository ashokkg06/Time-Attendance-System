package com.wipro.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.entity.User;
import com.wipro.payload.CreateUserRequest;
import com.wipro.payload.LoginUserRequest;
import com.wipro.payload.UpdateUserRequest;
import com.wipro.payload.UserResponse;
import com.wipro.security.JwtUtilities;
import com.wipro.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtilities jwtUtilities;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest user) {
		User savedUser = userService.createUser(user);
		URI savedUserUri = URI.create("/users/" + savedUser.getId());
		var userResponse = modelMapper.map(savedUser, UserResponse.class);
		userResponse.setToken(jwtUtilities.generateToken(user.getUsername(), user.getRole().toString()));
		return ResponseEntity.created(savedUserUri).body(userResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody LoginUserRequest req) throws Exception {
		User user = userService.loginUser(req.getEmail(), req.getPassword());
		var userResponse = modelMapper.map(user, UserResponse.class);
		userResponse.setToken(jwtUtilities.generateToken(user.getUsername(), user.getRole().toString()));
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if(users == null || users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/manager/{id}")
	public ResponseEntity<List<User>> getUserByManagerId(@PathVariable("id") Long managerId) {
		return ResponseEntity.ok(userService.getUsersByManagerId(managerId));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<User> getUserByUserName(@PathVariable String name) {
		return ResponseEntity.ok(userService.getUserByUsername(name));
	}
	
	@GetMapping("/parsetoken")
	public ResponseEntity<String> parseToken(@RequestParam("token") String token) {
		String username = jwtUtilities.extractUsername(token);
		return ResponseEntity.ok(username);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updatedUser) {
		return ResponseEntity.ok(userService.updateUser(id, updatedUser));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		String message = userService.deleteUser(id);
		return ResponseEntity.ok(message);
	}

}
