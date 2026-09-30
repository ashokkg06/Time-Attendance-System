package com.wipro.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.LeaveMaster;
import com.wipro.repository.LeaveMasterRepository;

@Service
public class LeaveMasterService {

    @Autowired
    private LeaveMasterRepository leaveMasterRepository;

    public List<LeaveMaster> getAllLeaveMasters() {
        return leaveMasterRepository.findAll();
    }

    public LeaveMaster getLeaveMasterById(Long id) {
        return leaveMasterRepository.findById(id).orElse(null);
    }

    public LeaveMaster createLeaveMaster(LeaveMaster leaveMaster) {
        return leaveMasterRepository.save(leaveMaster);
    }

    public void deleteLeaveMaster(Long id) {
        leaveMasterRepository.deleteById(id);
    }
}