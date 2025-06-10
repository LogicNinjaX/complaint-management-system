package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;

import java.util.UUID;

public interface OfficerService {

    UserRecord saveOfficerDetails(OfficerRegisterRequest request);

    UserRecord updateOfficerDetails(UUID officerId, OfficerUpdateRequest request);
}
