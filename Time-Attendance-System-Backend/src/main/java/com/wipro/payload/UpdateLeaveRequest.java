package com.wipro.payload;

import java.time.LocalDate;

import com.wipro.entity.LeaveType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeaveRequest {
    private String title;
    private String reason;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
}
