package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.exception.UserNotFoundException;

import java.util.UUID;

public interface OfficerService {

    OfficerDto saveOfficerDetails(OfficerRegisterRequest request);

    OfficerDto getOfficerDetails(UUID officerId) throws UserNotFoundException;

    void deleteOfficerDetails(UUID officerId);

    UserRecord updateOfficerDetails(UUID officerId, OfficerUpdateRequest request) throws UserNotFoundException, DepartmentNotFoundException;

}
