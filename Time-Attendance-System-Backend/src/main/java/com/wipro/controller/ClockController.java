package com.wipro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.entity.ClockInOut;
import com.wipro.service.ClockInOutService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/clock")
@RequiredArgsConstructor
public class ClockController {

	private final ClockInOutService clockInOutService;
	
	@GetMapping
	public ResponseEntity<List<ClockInOut>> getAllClocks() {
		List<ClockInOut> clocks = clockInOutService.getAllClocks();
		return ResponseEntity.ok(clocks);
	}
	
	@GetMapping("/attendance/{id}")
	public ResponseEntity<List<ClockInOut>> getClocksByAttendanceId(@PathVariable("id") Long attendanceId) {
		List<ClockInOut> records = clockInOutService.getClockRecordsByAttendanceId(attendanceId);
		return ResponseEntity.ok(records);
	}
}
