package com.wipro.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttendanceRequest {
	private Long userId;
	private LocalDate date;
}
