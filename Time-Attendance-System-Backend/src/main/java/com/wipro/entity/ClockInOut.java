package com.wipro.entity;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "clock_in_out")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClockInOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private LocalTime totalHours;

//    @ManyToOne
//    @JoinColumn(name = "employee_id", nullable = false)
//    @JsonIgnore
//    private User employee;
    
    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    @JsonIgnore
    private Attendance attendance;

	public ClockInOut(LocalDate date, LocalTime clockIn, LocalTime clockOut, Attendance attendance) {
		super();
		this.date = date;
		this.clockIn = clockIn;
		this.clockOut = clockOut;
		this.attendance = attendance;
	}

	public ClockInOut(LocalDate date, LocalTime clockIn, Attendance attendance) {
		this.date = date;
		this.clockIn = clockIn;
		this.attendance = attendance;
	}
    
}
