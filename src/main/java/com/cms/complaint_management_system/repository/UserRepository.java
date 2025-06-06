package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserRecord, UUID> {
}
