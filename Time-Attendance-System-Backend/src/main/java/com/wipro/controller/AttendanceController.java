package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.entity.Attendance;
import com.wipro.service.AttendanceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
	
	public final AttendanceService attendanceService;
	
	@GetMapping("/create/user/{id}")
	public ResponseEntity<Attendance> createAttendance(@PathVariable("id") Long userId) {
		Attendance attendance = attendanceService.createDailyAttendance(userId);
		return ResponseEntity.ok(attendance);
	}
	
	@GetMapping("/clockout/{id}")
	public ResponseEntity<Attendance> logoutAttendance(@PathVariable("id") Long attendanceId) {
		Attendance attendance = attendanceService.UpdateLogOutAttendance(attendanceId);
		return ResponseEntity.ok(attendance);
	}
	
	@GetMapping("/clockin/{id}")
	public ResponseEntity<Attendance> loginAttendance(@PathVariable("id") Long attendanceId) {
		Attendance attendance = attendanceService.UpdateLogInAttendance(attendanceId);
		return ResponseEntity.ok(attendance);
	}
}
