package com.cms.complaint_management_system.controller;

import com.cms.complaint_management_system.dto.ComplaintDto;
import com.cms.complaint_management_system.dto.ComplaintWithoutUserDto;
import com.cms.complaint_management_system.dto.api_request.ComplaintRegisterRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.enums.ComplaintStatus;
import com.cms.complaint_management_system.security.CustomUserDetails;
import com.cms.complaint_management_system.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/complaints")
    public ResponseEntity<GeneralResponse<ComplaintDto>> registerComplaint(@RequestBody ComplaintRegisterRequest request, @AuthenticationPrincipal CustomUserDetails user){
        var complaint = complaintService.registerComplaint(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse<>(true, "Complaint has been successfully registered", complaint));
    }

    @GetMapping("/complaints")
    public ResponseEntity<GeneralResponse<List<ComplaintWithoutUserDto>>> getComplaints(
            @RequestParam(defaultValue = "1", required = false, name = "page") int pageNumber,
            @RequestParam(defaultValue = "10", required = false, name = "size") int pageSize,
            @RequestParam(defaultValue = "pending", required = false) String status,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    )
    {

        ComplaintStatus complaintStatus;
        try {
            complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid complaint status: " + status);
        }

        var complaintList = complaintService
                .getComplainsByStatus(complaintStatus ,pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Complaints fetched successfully", complaintList));
    }



}
