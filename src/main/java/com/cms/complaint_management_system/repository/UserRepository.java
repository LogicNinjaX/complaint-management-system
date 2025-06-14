package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.dto.CitizenDto;
import com.cms.complaint_management_system.dto.OfficerDto;
import com.cms.complaint_management_system.entity.UserRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserRecord, UUID> {

    Optional<UserRecord> findByUsername(String username);

    @Query("SELECT new com.cms.complaint_management_system.dto.CitizenDto(u.username, u.password, u.email, u.role, u.createdAt, u.updatedAt) FROM UserRecord u WHERE u.userId = :citizenId")
    Optional<CitizenDto> getCitizenDetails(UUID citizenId);

    @Query("SELECT new com.cms.complaint_management_system.dto.OfficerDto(u.username, u.password, u.email, u.role, u.department, u.createdAt, u.updatedAt) FROM UserRecord u WHERE u.userId = :officerId")
    Optional<OfficerDto> getOfficerDetails(UUID officerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRecord u WHERE u.userId = :citizenId AND u.role = UserRoles.CITIZEN")
    void deleteCitizenDetails(UUID citizenId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRecord u WHERE u.userId = :officerId AND u.role = UserRoles.OFFICER")
    void deleteOfficerDetails(UUID officerId);
}
