package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.dto.api_request.OfficerUpdateRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.entity.UserRecord;
import com.cms.complaint_management_system.security.CustomUserDetails;
import com.cms.complaint_management_system.service.OfficerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class OfficerController {

    private final OfficerService officerService;

    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @GetMapping("/officers")
    public ResponseEntity<GeneralResponse<OfficerDto>> getOfficer(@AuthenticationPrincipal CustomUserDetails user){
        var officerEntity = officerService.getOfficerDetails(user.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Data retrieved successfully", officerEntity));
    }

    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @DeleteMapping("/officers")
    public ResponseEntity<GeneralResponse<?>> deleteOfficer(@AuthenticationPrincipal CustomUserDetails user){
        officerService.deleteOfficerDetails(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Officer deleted successfully", null));
    }

    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @PutMapping("/officers")
    public ResponseEntity<GeneralResponse<UserRecord>> updateOfficer(@AuthenticationPrincipal CustomUserDetails user,
                                                           @Valid @RequestBody OfficerUpdateRequest request){

        var updatedOfficer = officerService.updateOfficerDetails(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Officer updated successfully", updatedOfficer));
    }


}
