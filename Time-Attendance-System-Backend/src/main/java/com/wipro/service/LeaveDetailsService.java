package com.wipro.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.LeaveDetails;
import com.wipro.repository.LeaveDetailsRepository;

@Service
public class LeaveDetailsService {

    @Autowired
    private LeaveDetailsRepository leaveDetailsRepository;

    public List<LeaveDetails> getAllLeaveDetails() {
        return leaveDetailsRepository.findAll();
    }

    public LeaveDetails getLeaveDetailsById(Long id) {
        return leaveDetailsRepository.findById(id).orElse(null);
    }

    public LeaveDetails createLeaveDetails(LeaveDetails leaveDetails) {
        return leaveDetailsRepository.save(leaveDetails);
    }

    public String deleteLeaveDetails(Long id) {
        leaveDetailsRepository.deleteById(id);
        return "Successfully deleted leave details";
    }
}