package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.ComplaintRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<ComplaintRecord, UUID> {
}
