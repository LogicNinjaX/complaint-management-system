package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.dto.api_request.CitizenRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.CitizenUpdateRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.dto.api_response.UserUpdateResponse;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.security.CustomUserDetails;
import com.cms.complaint_management_system.service.CitizenService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CitizenController {

    private final CitizenService citizenService;
    private final ModelMapper modelMapper;

    public CitizenController(CitizenService citizenService, ModelMapper modelMapper) {
        this.citizenService = citizenService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/citizens")
    public ResponseEntity<GeneralResponse<CitizenDto>> registerCitizen(@Valid @RequestBody CitizenRegisterRequest registerRequest){
        var userEntity = modelMapper.map(registerRequest, UserRecord.class);

        var savedUserEntity = citizenService.saveCitizenDetails(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Citizen details saved successfully", savedUserEntity));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @GetMapping("/citizens")
    public ResponseEntity<GeneralResponse<CitizenDto>> getCitizen(@AuthenticationPrincipal CustomUserDetails user){
        var userEntity = citizenService.getCitizenDetails(user.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Data retrieved successfully", userEntity));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @DeleteMapping("/citizens")
    public ResponseEntity<GeneralResponse<?>> deleteCitizen(@AuthenticationPrincipal CustomUserDetails user){
        citizenService.deleteCitizenDetails(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Citizen deleted successfully", null));
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN')")
    @PutMapping("/citizens")
    public ResponseEntity<GeneralResponse<?>> updateUserDetails(@Valid @RequestBody CitizenUpdateRequest request,
                                                                @AuthenticationPrincipal CustomUserDetails user){

        var newUser = citizenService.updateCitizenDetails(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true,
                        "Citizen updated successfully",
                        modelMapper.map(newUser, UserUpdateResponse.class)));
    }
}
