package com.cms.complaint_management_system.service.impl;


import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.entity.Departments;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.exception.UserNotFoundException;
import com.cms.complaint_management_system.repository.DepartmentRepository;
import com.cms.complaint_management_system.repository.UserRepository;
import com.cms.complaint_management_system.service.OfficerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfficerServiceImpl implements OfficerService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public OfficerServiceImpl(UserRepository userRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public OfficerDto saveOfficerDetails(OfficerRegisterRequest request){
        Departments departmentEntity = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with: "+request.getDepartmentId()));

        UserRecord user = modelMapper.map(request, UserRecord.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRoles.OFFICER);
        user.setDepartment(departmentEntity);

        var newOfficer = userRepository.save(user);
        return modelMapper.map(newOfficer, OfficerDto.class);
    }

    @Transactional
    @Override
    public UserRecord updateOfficerDetails(UUID officerId, OfficerUpdateRequest request) {
        UserRecord user = userRepository.findById(officerId)
                .orElseThrow(() -> new UserNotFoundException("Officer not found"));

        if (user.getRole() != UserRoles.OFFICER){
            throw new UserNotFoundException("Officer not found");
        }

        Departments departmentEntity = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with: "+request.getDepartmentId()));

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setDepartment(departmentEntity);

        return userRepository.save(user);
    }

    @Override
    public OfficerDto getOfficerDetails(UUID officerId) throws UserNotFoundException {
        OfficerDto user =  userRepository.getOfficerDetails(officerId)
                .orElseThrow(() -> new UserNotFoundException("Officer not found"));

        if (user.getRole() != UserRoles.OFFICER){
            throw new UserNotFoundException("Officer not found with id: "+officerId);
        }
        return user;
    }


    @Override
    public void deleteOfficerDetails(UUID officerId) {
        userRepository.deleteOfficerDetails(officerId);
    }
}
