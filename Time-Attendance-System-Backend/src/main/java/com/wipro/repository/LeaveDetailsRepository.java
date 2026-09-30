package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.LeaveDetails;

@Repository
public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long>{

}
