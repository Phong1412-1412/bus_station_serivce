package com.busstation.services;

import org.springframework.data.domain.Page;

import com.busstation.entities.Leave;
import com.busstation.payload.request.ApproveLeaveRequest;
import com.busstation.payload.request.LeaveRequest;
import com.busstation.payload.response.LeaveResponse;

public interface LeaveService {
	Page<LeaveResponse> showAllLeave(int pageNumber, int pageSize);

	Page<LeaveResponse> showAllLeaveActive(int pageNumber, int pageSize);

//    LeaveResponse updatedLeave(String leaveId, LeaveRequest request);

	LeaveResponse addLeave(LeaveRequest request);

	Leave updateLeaveStatus(String leaveId, ApproveLeaveRequest request);

	Boolean deleteLeave(String leaveId);

	LeaveResponse getLeaveResponse(Leave newLeave);

	Page<LeaveResponse> findByUserId(String userId, int pageNumber, int pageSize);
}
