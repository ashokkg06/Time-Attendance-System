package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.ClockInOut;

@Repository
public interface ClockRepository extends JpaRepository<ClockInOut, Long> {
	List<ClockInOut> findByAttendanceId(Long id);
}
