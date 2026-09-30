package com.wipro.service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.ClockInOut;
import com.wipro.entity.User;
import com.wipro.repository.ClockRepository;
import com.wipro.repository.UserRepository;

@Service
public class ClockInOutService {

    @Autowired
    private ClockRepository clockRepository;

    @Autowired
    private UserRepository userRepository;
    
    public List<ClockInOut> getAllClocks() {
    	return clockRepository.findAll();
    }

//    public ClockInOut clockIn(Long userId) {
//
//        ClockInOut clockInOut = new ClockInOut();
//        clockInOut.setDate(LocalDate.now());
//        clockInOut.setClockIn(LocalTime.now());
//
//        return clockRepository.save(clockInOut);
//    }
//
//    public ClockInOut clockOut(Long clockInOutId) {
//        return clockRepository.findById(clockInOutId).map(clockInOut -> {
//            clockInOut.setClockOut(LocalTime.now());
//            return clockRepository.save(clockInOut);
//        }).get();
//    }
    
    public List<ClockInOut> getClockRecordsByAttendanceId(Long attendanceId) {
        return clockRepository.findByAttendanceId(attendanceId);
    }
}
