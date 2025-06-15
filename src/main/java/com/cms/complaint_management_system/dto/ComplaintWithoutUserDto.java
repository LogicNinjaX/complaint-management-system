package com.cms.complaint_management_system.dto;

import com.cms.complaint_management_system.entity.ComplaintCategories;
import com.cms.complaint_management_system.enums.ComplaintStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ComplaintWithoutUserDto {

    private UUID complaintId;

    private ComplaintCategories category;

    private String title;

    private String description;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ComplaintStatus status;

    public ComplaintWithoutUserDto() {
    }

    public ComplaintWithoutUserDto(UUID complaintId, ComplaintCategories category, String title, String description, String address, LocalDateTime createdAt, LocalDateTime updatedAt, ComplaintStatus status) {
        this.complaintId = complaintId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public UUID getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(UUID complaintId) {
        this.complaintId = complaintId;
    }

    public ComplaintCategories getCategory() {
        return category;
    }

    public void setCategory(ComplaintCategories category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }
}
