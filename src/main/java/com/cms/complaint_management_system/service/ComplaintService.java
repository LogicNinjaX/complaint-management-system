package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.dto.ComplaintDto;
import com.cms.complaint_management_system.dto.ComplaintWithoutUserDto;
import com.cms.complaint_management_system.dto.api_request.ComplaintRegisterRequest;
import com.cms.complaint_management_system.enums.ComplaintStatus;
import com.cms.complaint_management_system.exception.CategoryNotFoundException;
import com.cms.complaint_management_system.exception.ComplaintNotFoundException;
import com.cms.complaint_management_system.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ComplaintService {

    ComplaintDto registerComplaint(UUID userId, ComplaintRegisterRequest request) throws UserNotFoundException, CategoryNotFoundException;

    List<ComplaintWithoutUserDto> getComplainsByStatus(ComplaintStatus status, int pageNumber, int pageSize, String sortBy, String sortDir);

    ComplaintWithoutUserDto getComplainByComplaintId(UUID complaintId) throws ComplaintNotFoundException;

    List<ComplaintWithoutUserDto> getComplainsByUserId(UUID userId, int pageNumber, int pageSize, String sortBy, String sortDir);

    void updateComplaintStatus(UUID complaintId, ComplaintStatus status) throws ComplaintNotFoundException;
}
