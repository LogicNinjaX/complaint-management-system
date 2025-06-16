package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.Departments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Departments d WHERE d.departmentId = :departmentId")
    void deleteDepartmentById(long departmentId);
}
