package com.wipro.service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.LeaveDetails;
import com.wipro.entity.LeaveRequest;
import com.wipro.entity.LeaveType;
import com.wipro.entity.User;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.payload.LeaveRequestPayload;
import com.wipro.payload.UpdateLeaveRequest;
import com.wipro.repository.LeaveDetailsRepository;
import com.wipro.repository.LeaveRequestRepository;
import com.wipro.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LeaveRequestService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	@Autowired
	private LeaveDetailsRepository leaveDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	public LeaveRequest createLeaveRequest(Long userId, LeaveRequestPayload leaveRequest) {
		User employee = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		LeaveRequest req = new LeaveRequest();

		req.setDate(LocalDate.now());
		req.setTitle(leaveRequest.getTitle());
		req.setReason(leaveRequest.getReason());
		req.setLeaveType(leaveRequest.getLeaveType());
		req.setStartDate(leaveRequest.getStartDate());
		req.setEndDate(leaveRequest.getEndDate());
		req.setEmployee(employee);
		req.setStatus("PENDING");

		return leaveRequestRepository.save(req);
	}

	public List<LeaveRequest> getLeaveRequestsByUser(Long userId) {
		return leaveRequestRepository.findByEmployeeId(userId);
	}

	public LeaveRequest updateLeaveStatus(Long leaveRequestId, String status) {
		return leaveRequestRepository.findById(leaveRequestId).map(leaveRequest -> {
			log.info(status);

			User user = leaveRequest.getEmployee();
			LeaveType requestLeaveType = leaveRequest.getLeaveType();
			long leaveDays = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

			LeaveDetails leaveDetails = user.getLeaveDetails().stream()
					.filter(details -> details.getLeaveType().equals(requestLeaveType))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("No leave details found for leave type: " + requestLeaveType));

			if (leaveRequest.getStatus().equals("ACCEPTED") && status.equalsIgnoreCase("REJECTED")) {
				log.info("Leave was already accepted, reverting the leave days");
				leaveDetails.setPendingLeaves(leaveDetails.getPendingLeaves() + (int) leaveDays);
				leaveDetailsRepository.save(leaveDetails);
			} 
			else if (status.equalsIgnoreCase("ACCEPTED")) {
				log.info("LeaveDays: " + leaveDays);
				log.info("Pending Leaves: " + leaveDetails.getPendingLeaves());

				if (leaveDetails.getPendingLeaves() >= leaveDays) {
					leaveDetails.setPendingLeaves(leaveDetails.getPendingLeaves() - (int) leaveDays);
					log.info("Leave is reduced");
					leaveDetailsRepository.save(leaveDetails);
				} else {
					throw new IllegalStateException("Not enough pending leaves.");
				}
			}

			log.info("Leave request id: " + leaveRequest.getId());
			leaveRequest.setStatus(status);

			return leaveRequestRepository.save(leaveRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
	}

	public LeaveRequest updateLeaveRequest(Long leaveRequestId, UpdateLeaveRequest req) {
		return leaveRequestRepository.findById(leaveRequestId).
				map(request -> {
					request.setTitle(req.getTitle());
					request.setReason(req.getReason());
					request.setLeaveType(req.getLeaveType());
					request.setStartDate(req.getStartDate());
					request.setEndDate(req.getEndDate());

					return leaveRequestRepository.save(request);
				}).orElseThrow(() -> new ResourceNotFoundException("LeaveRequest with Id: " + leaveRequestId + " Not Found"));
	}
}
