package com.wipro.service;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.entity.LeaveDetails;
import com.wipro.entity.LeaveMaster;
import com.wipro.entity.Role;
import com.wipro.entity.User;
import com.wipro.exception.InvalidCredentialsException;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.exception.UserAlreadyExistsException;
import com.wipro.exception.UserNotFoundException;
import com.wipro.payload.CreateUserRequest;
import com.wipro.payload.UpdateUserRequest;
import com.wipro.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
	private final LeaveDetailsService leaveDetailsService;
	private final LeaveMasterService leaveMasterService;
	
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public List<User> getUsersByManagerId(Long managerId) {
        return userRepository.findByManagerId(managerId).orElseThrow(() -> new ResourceNotFoundException("Users with manager id: " + managerId + " not found"));
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with user id: " + id + " not found"));
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User createUser(CreateUserRequest req) {
    	if(userRepository.findByEmail(req.getEmail()).isEmpty() && userRepository.findByUsername(req.getUsername()).isEmpty()) {
    		User user = modelMapper.map(req, User.class);
    		user.setId(null);
    		user.setManagerId(req.getManagerId());
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		user.setRole(Enum.valueOf(Role.class, req.getRole().toString()));

    		List<LeaveMaster> leaveMaster = leaveMasterService.getAllLeaveMasters();
    		for(LeaveMaster leaveList : leaveMaster) {
    			LeaveDetails leave = new LeaveDetails();
    			
        		leave.setEmployee(user);
        		leave.setLeaveType(leaveList.getLeaveType());
        		leave.setPendingLeaves(leaveList.getTotalLeaves());
        		
        		user.setLeaveDetails(new LinkedList<>());
        		user.getLeaveDetails().add(leave);
    		}
    		System.out.println(user);
    		return userRepository.save(user);
    	}
    	else {
    		throw new UserAlreadyExistsException(req.getUsername());
    	}
    }
    
    public User loginUser(String email, String password) {
    	var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    	if(!passwordEncoder.matches(password, user.getPassword()))
    		throw new InvalidCredentialsException();
    	return user;
    }

    public User updateUser(Long id, UpdateUserRequest updatedUser) {
    	return userRepository.findById(id)
    			.map(user -> {
		            user.setName(updatedUser.getName());
		            user.setDob(updatedUser.getDob());
		            user.setAddress(updatedUser.getAddress());
		            user.setMobile(updatedUser.getMobile());
		            return userRepository.save(user);
		        })
    			.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Id: " + id + " is deleted";
    }
}