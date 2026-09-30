package com.wipro.controller;

import com.wipro.entity.LeaveMaster;
import com.wipro.service.LeaveMasterService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@SecurityRequirement(name = "admin")
@RequestMapping("/api/leavemaster")
public class LeaveMasterController {

    @Autowired
    private LeaveMasterService leaveMasterService;

    @GetMapping
    public List<LeaveMaster> getAllLeaveMasters() {
        return leaveMasterService.getAllLeaveMasters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveMaster> getLeaveMasterById(@PathVariable Long id) {
        LeaveMaster leaveMaster = leaveMasterService.getLeaveMasterById(id);
        if (leaveMaster != null) {
            return ResponseEntity.ok(leaveMaster);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public LeaveMaster createLeaveMaster(@RequestBody LeaveMaster leaveMaster) {
        return leaveMasterService.createLeaveMaster(leaveMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveMaster(@PathVariable Long id) {
        leaveMasterService.deleteLeaveMaster(id);
        return ResponseEntity.noContent().build();
    }
}
