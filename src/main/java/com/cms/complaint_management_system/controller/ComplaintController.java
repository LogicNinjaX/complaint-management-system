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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Role -> Citizen
    @PostMapping("/complaints")
    public ResponseEntity<GeneralResponse<ComplaintDto>> registerComplaint(@RequestBody ComplaintRegisterRequest request, @AuthenticationPrincipal CustomUserDetails user){
        var complaint = complaintService.registerComplaint(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse<>(true, "Complaint has been successfully registered", complaint));
    }

    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
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

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/complaints/my")
    public ResponseEntity<GeneralResponse<List<ComplaintWithoutUserDto>>> getMyComplaints(
            @RequestParam(defaultValue = "1", required = false, name = "page") int pageNumber,
            @RequestParam(defaultValue = "10", required = false, name = "size") int pageSize,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir,
            @AuthenticationPrincipal CustomUserDetails user
    )
    {
        var complaintList = complaintService.getComplainsByUserId(user.getUserId(), pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Complaints fetched successfully", complaintList));
    }

    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @GetMapping("/complaints/{complaint-id}")
    public ResponseEntity<GeneralResponse<?>> getByComplaintById(@PathVariable("complaint-id") UUID complaintId){
        var complaint = complaintService.getComplainByComplaintId(complaintId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Complaint fetched successfully", complaint));
    }


    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @PutMapping("/complaints/{complaint-id}/status")
    public ResponseEntity<GeneralResponse<?>> updateComplaintStatus(
            @PathVariable("complaint-id") UUID complaintId,
            @RequestParam String status)
    {

        ComplaintStatus complaintStatus;
        try {
            complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid complaint status: " + status);
        }

        complaintService.updateComplaintStatus(complaintId, complaintStatus);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Status updated successfully", null));
    }


    @PreAuthorize("hasAnyRole('OFFICER', 'ADMIN')")
    @DeleteMapping("/complaints/{complaint-id}")
    public ResponseEntity<GeneralResponse<?>> deleteComplaint(@PathVariable("complaint-id") UUID complaintId){
        complaintService.deleteComplaintByComplaintId(complaintId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Complaint deleted successfully", null));
    }

}
