package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.ComplaintCategories;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategories, Long> {


    @Query("SELECT c FROM ComplaintCategories c WHERE c.departmentId = :departmentId")
    Page<ComplaintCategories> getComplaintCategoriesByDepartmentId(long departmentId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM ComplaintCategories c WHERE c.categoryId = :categoryId")
    void deleteCategoryById(long categoryId);
}
