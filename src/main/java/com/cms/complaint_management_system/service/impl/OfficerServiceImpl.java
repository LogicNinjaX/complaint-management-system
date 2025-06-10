package com.cms.complaint_management_system.service.impl;


import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.repository.DepartmentRepository;
import com.cms.complaint_management_system.service.OfficerService;
import com.cms.complaint_management_system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfficerServiceImpl implements OfficerService {

    private final UserService userService;
    private final DepartmentRepository departmentRepository;

    public OfficerServiceImpl(UserService userService, DepartmentRepository departmentRepository) {
        this.userService = userService;
        this.departmentRepository = departmentRepository;
    }


    @Override
    public UserRecord saveOfficerDetails(OfficerRegisterRequest request){
        var departmentEntity = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with: "+request.getDepartmentId()));

        var userRecord = new UserRecord(request.getUsername(), request.getPassword(), request.getEmail(), departmentEntity);
        userRecord.setRole(UserRoles.OFFICER);
        return userService.saveUserDetails(userRecord);
    }

    @Override
    public UserRecord updateOfficerDetails(UUID officerId, OfficerUpdateRequest request) {
        var oldOfficer = userService.getUserDetails(officerId);
        var departmentEntity = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with: "+request.getDepartmentId()));

        oldOfficer.setUsername(request.getUsername());
        oldOfficer.setPassword(request.getPassword());
        oldOfficer.setEmail(request.getEmail());
        oldOfficer.setDepartment(departmentEntity);

        return userService.saveUserDetails(oldOfficer);
    }
}
