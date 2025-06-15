package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.dto.ComplaintWithoutUserDto;
import com.cms.complaint_management_system.entity.ComplaintRecord;
import com.cms.complaint_management_system.enums.ComplaintStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<ComplaintRecord, UUID> {

    @Query("SELECT new com.cms.complaint_management_system.dto.ComplaintWithoutUserDto(c.complaintId, c.category, c.title, c.description, c.address, c.createdAt, c.updatedAt, c.status) FROM ComplaintRecord c WHERE c.status = :status")
    Page<ComplaintWithoutUserDto> getComplainsByStatus(ComplaintStatus status, Pageable pageable);

    @Query("SELECT new com.cms.complaint_management_system.dto.ComplaintWithoutUserDto(c.complaintId, c.category, c.title, c.description, c.address, c.createdAt, c.updatedAt, c.status) FROM ComplaintRecord c WHERE c.complaintId = :complaintId")
    Optional<ComplaintWithoutUserDto> getComplainByComplaintId(UUID complaintId);

    @Query("SELECT new com.cms.complaint_management_system.dto.ComplaintWithoutUserDto(c.complaintId, c.category, c.title, c.description, c.address, c.createdAt, c.updatedAt, c.status) FROM ComplaintRecord c WHERE c.user.userId :userId")
    Page<ComplaintWithoutUserDto> getComplainsByUserId(UUID userId, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE ComplaintRecord c SET c.status = :status WHERE c.complaintId = :complaintId")
    int updateComplaintStatus(UUID complaintId, ComplaintStatus status);
}
