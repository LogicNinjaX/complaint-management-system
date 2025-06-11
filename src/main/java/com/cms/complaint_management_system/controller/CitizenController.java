package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.api_request.CitizenRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.UserUpdateRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.dto.api_response.UserUpdateResponse;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CitizenController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public CitizenController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/citizens")
    public ResponseEntity<GeneralResponse<UserRecord>> registerCitizen(@Valid @RequestBody CitizenRegisterRequest registerRequest){
        var userEntity = modelMapper.map(registerRequest, UserRecord.class);
        userEntity.setRole(UserRoles.CITIZEN);

        var savedUserEntity = userService.saveUserDetails(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Citizen details saved successfully", savedUserEntity));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @GetMapping("/citizens/{citizen-id}")
    public ResponseEntity<GeneralResponse<?>> getCitizen(@PathVariable("citizen-id") UUID citizenId){
        var userEntity = userService.getUserDetails(citizenId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Data retrieved successfully", userEntity));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @DeleteMapping("/citizens/{citizen-id}")
    public ResponseEntity<GeneralResponse<?>> deleteCitizen(@PathVariable("citizen-id") UUID citizenId){
        userService.deleteUserDetails(citizenId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Citizen deleted successfully", null));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @PutMapping("/citizens/{citizen-id}")
    public ResponseEntity<GeneralResponse<?>> updateUserDetails(@Valid @PathVariable("citizen-id") UUID citizenId,
                                                                @RequestBody UserUpdateRequest request){

        var newUser = userService.updateUserDetails(citizenId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true,
                        "Citizen updated successfully",
                        modelMapper.map(newUser, UserUpdateResponse.class)));
    }
}
