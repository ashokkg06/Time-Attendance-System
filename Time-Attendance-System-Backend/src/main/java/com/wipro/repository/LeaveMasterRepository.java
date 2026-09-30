package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.LeaveMaster;

@Repository
public interface LeaveMasterRepository extends JpaRepository<LeaveMaster, Long> {

}
