package com.wipro.controller;

import com.wipro.entity.LeaveRequest;
import com.wipro.payload.LeaveRequestPayload;
import com.wipro.payload.UpdateLeaveRequest;
import com.wipro.service.LeaveRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/leaverequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/user/{userId}")
    public LeaveRequest createLeaveRequest(@PathVariable Long userId, @RequestBody LeaveRequestPayload leaveRequest) {
        return leaveRequestService.createLeaveRequest(userId, leaveRequest);
    }

    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getLeaveRequestsByUser(@PathVariable Long userId) {
        return leaveRequestService.getLeaveRequestsByUser(userId);
    }

    @PutMapping("/{leaveRequestId}/status")
    public ResponseEntity<LeaveRequest> updateLeaveStatus(@PathVariable("leaveRequestId") Long leaveRequestId, @RequestBody String status) {
    	log.info("Status: " + status);
        LeaveRequest updatedLeaveRequest = leaveRequestService.updateLeaveStatus(leaveRequestId, status);
        return ResponseEntity.ok(updatedLeaveRequest);
    }
    
    @PutMapping("/{leaveRequestId}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable Long leaveRequestId, @RequestBody UpdateLeaveRequest req) {
        LeaveRequest updatedLeaveRequest = leaveRequestService.updateLeaveRequest(leaveRequestId, req);
        return ResponseEntity.ok(updatedLeaveRequest);
    }
}
