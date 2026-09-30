package com.wipro.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String title;
    private String reason;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore
    private User employee;
    
	public LeaveRequest(LocalDate date, String title, String reason, LocalDate startDate,
			LocalDate endDate, String status, User employee) {
		super();
		this.employee = employee;
		this.date = date;
		this.title = title;
		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}
    
}

