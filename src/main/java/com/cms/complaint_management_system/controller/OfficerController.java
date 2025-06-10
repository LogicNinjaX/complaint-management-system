package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.api_request.OfficerRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.enums.UserRoles;
import com.cms.complaint_management_system.exception.OfficerException;
import com.cms.complaint_management_system.service.OfficerService;
import com.cms.complaint_management_system.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OfficerController {

    private final OfficerService officerService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfficerController(OfficerService officerService, UserService userService, ModelMapper modelMapper) {
        this.officerService = officerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/officers") // roles allowed -> admin
    public ResponseEntity<GeneralResponse<?>> registerOfficer(@Valid @RequestBody OfficerRegisterRequest request){
        var officerEntity = officerService.saveOfficerDetails(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Officer details saved successfully", officerEntity));
    }

    @GetMapping("/officers/{officer-id}")
    public ResponseEntity<GeneralResponse<UserRecord>> getOfficer(@PathVariable("officer-id") UUID officerId){
        var officerEntity = userService.getUserDetails(officerId);

        if (!(officerEntity.getRole() == UserRoles.OFFICER)){
            throw new OfficerException("user with id:+"+officerId+" is not a officer");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Data retrieved successfully", officerEntity));
    }

    @DeleteMapping("/officers/{officer-id}")
    public ResponseEntity<GeneralResponse<?>> deleteOfficer(@PathVariable("officer-id") UUID officerId){
        userService.deleteUserDetails(officerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Officer deleted successfully", null));
    }

    @PutMapping("/officers/{officer-id}")
    public ResponseEntity<GeneralResponse<UserRecord>> updateOfficer(@PathVariable("officer-id") UUID officerId,
                                                           @Valid @RequestBody OfficerUpdateRequest request){

        var updatedOfficer = officerService.updateOfficerDetails(officerId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Officer updated successfully", updatedOfficer));
    }


}
