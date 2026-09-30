package com.wipro.entity;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_master")
@Getter
@Setter
@NoArgsConstructor
public class LeaveMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private Integer totalLeaves;
    
//    @OneToMany(mappedBy = "leavemaster")
//    @JsonIgnore
//    private List<LeaveDetails> leaveDetails = new ArrayList<>();
    
	public LeaveMaster(LeaveType leaveType, Integer totalLeaves) {
		super();
		this.leaveType = leaveType;
		this.totalLeaves = totalLeaves;
	}
    
    
}