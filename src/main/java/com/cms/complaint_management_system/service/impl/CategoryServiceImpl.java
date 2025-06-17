package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.entity.ComplaintCategories;
import com.cms.complaint_management_system.exception.CategoryException;
import com.cms.complaint_management_system.exception.CategoryNotFoundException;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.repository.ComplaintCategoryRepository;
import com.cms.complaint_management_system.repository.DepartmentRepository;
import com.cms.complaint_management_system.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ComplaintCategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;

    public CategoryServiceImpl(ComplaintCategoryRepository categoryRepository, DepartmentRepository departmentRepository) {
        this.categoryRepository = categoryRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<ComplaintCategories> getCategoryList(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageable;

        try {
            Sort.Direction direction = Sort.Direction.valueOf(sortDir.toUpperCase());
            pageable  = PageRequest.of(pageNumber-1, pageSize, Sort.by(direction, sortBy));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid complaint status: " + sortBy);
        }

        return categoryRepository.findAll(pageable).toList();
    }

    @Override
    public List<ComplaintCategories> getCategoriesByDepartmentId(long departmentId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageable;

        try {
            Sort.Direction direction = Sort.Direction.valueOf(sortDir.toUpperCase());
            pageable  = PageRequest.of(pageNumber-1, pageSize, Sort.by(direction, sortBy));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid complaint status: " + sortBy);
        }

        return categoryRepository.getComplaintCategoriesByDepartmentId(departmentId, pageable).toList();
    }

    @Override
    public ComplaintCategories saveCategory(ComplaintCategories category) {

        if (!departmentRepository.existsById(category.getDepartmentId())){
            throw new DepartmentNotFoundException("Department not found with id: "+category.getDepartmentId());
        }

        if (categoryRepository.existsById(category.getCategoryId())){
            throw new CategoryException("Category already exist with id: "+category.getCategoryId());
        }

        ComplaintCategories complaintCategories;

        try{
            complaintCategories = categoryRepository.save(category);
        }catch (DataIntegrityViolationException e){
            throw  new CategoryException("Category already exist");
        }

        return complaintCategories;
    }

    @Override
    public ComplaintCategories updateCategory(long categoryId, ComplaintCategories category) {
        var categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "+categoryId));

        categoryEntity.setCategoryName(category.getCategoryName());

        ComplaintCategories complaintCategory;
        try{
            complaintCategory = categoryRepository.save(categoryEntity);
        }catch (DataIntegrityViolationException e){
            throw  new CategoryException("Category already exist");
        }

        return complaintCategory;
    }

    @Override
    public void deleteCategory(long categoryId) {
        categoryRepository.deleteCategoryById(categoryId);
    }
}
