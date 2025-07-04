package com.cms.complaint_management_system.entity;

import com.cms.complaint_management_system.enums.ComplaintStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "complaints")
public class ComplaintRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID complaintId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId")
    private ComplaintCategories category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status = ComplaintStatus.PENDING;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserRecord user;

    public UUID getComplaintId() {
        return complaintId;
    }

    public ComplaintRecord() {
    }

    public ComplaintRecord(String title, String description, String address, UserRecord user) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.user = user;
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

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }
}
