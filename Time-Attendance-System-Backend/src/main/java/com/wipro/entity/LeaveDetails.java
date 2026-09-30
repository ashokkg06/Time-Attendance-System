package com.wipro.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_details")
@Getter
@Setter
@NoArgsConstructor
public class LeaveDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private Integer pendingLeaves;
    
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "leavemaster_id", nullable = false)
//    private LeaveMaster leavemaster;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore
    private User employee;

	public LeaveDetails(LeaveType leaveType, Integer pendingLeaves, User employee) {
		super();
		this.leaveType = leaveType;
		this.pendingLeaves = pendingLeaves;
		this.employee = employee;
	}

}