package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.dto.api_request.CitizenUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CitizenService {

    CitizenDto saveCitizenDetails(UserRecord userRecord);

    CitizenDto getCitizenDetails(UUID citizenId) throws UserNotFoundException;

    void deleteCitizenDetails(UUID citizenId);

    CitizenDto updateCitizenDetails(UUID citizenId, CitizenUpdateRequest request);
}
