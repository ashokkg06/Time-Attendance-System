package com.wipro.controller;

import com.wipro.entity.LeaveDetails;
import com.wipro.service.LeaveDetailsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/leavedetails")
public class LeaveDetailsController {

    @Autowired
    private LeaveDetailsService leaveDetailsService;

    @GetMapping
    public List<LeaveDetails> getAllLeaveDetails() {
        return leaveDetailsService.getAllLeaveDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveDetails> getLeaveDetailsById(@PathVariable Long id) {
        LeaveDetails leaveDetails = leaveDetailsService.getLeaveDetailsById(id);
        if (leaveDetails != null) {
            return ResponseEntity.ok(leaveDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public LeaveDetails createLeaveDetails(@RequestBody LeaveDetails leaveDetails) {
        return leaveDetailsService.createLeaveDetails(leaveDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveDetails(@PathVariable Long id) {
        String msg = leaveDetailsService.deleteLeaveDetails(id);
        return ResponseEntity.ok(msg);
    }
}
