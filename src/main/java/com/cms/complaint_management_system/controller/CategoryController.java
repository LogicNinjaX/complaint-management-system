package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.api_request.CategoryRegisterRequest;
import com.cms.complaint_management_system.dto.api_request.CategoryUpdateRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.entity.ComplaintCategories;
import com.cms.complaint_management_system.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/categories")
    public ResponseEntity<GeneralResponse<List<ComplaintCategories>>> getCategories(
            @RequestParam(defaultValue = "1", name = "page", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", name = "size", required = false) int pageSize,
            @RequestParam(defaultValue = "categoryName", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    )
    {
        var categoryList = categoryService.getCategoryList(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Categories fetched successfully", categoryList));
    }

    @GetMapping("/categories/{department-id}")
    public ResponseEntity<GeneralResponse<List<ComplaintCategories>>> getCategoriesByDepartmentId(
            @PathVariable("department-id") long departmentId,
            @RequestParam(defaultValue = "1", name = "page", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", name = "size", required = false) int pageSize,
            @RequestParam(defaultValue = "categoryName", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    )
    {
        var categoryList = categoryService
                .getCategoriesByDepartmentId(departmentId, pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Categories fetched successfully", categoryList));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<GeneralResponse<ComplaintCategories>> saveCategory(@Valid @RequestBody CategoryRegisterRequest request){
        var savedCategory = categoryService.saveCategory(modelMapper.map(request, ComplaintCategories.class));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralResponse<>(true, "Category saved successfully", savedCategory));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/categories/{category-id}")
    public ResponseEntity<GeneralResponse<ComplaintCategories>> updateCategory(
            @PathVariable("category-id") long categoryId, @Valid @RequestBody CategoryUpdateRequest request
    )
    {
        var savedCategory = categoryService.updateCategory(categoryId, modelMapper.map(request, ComplaintCategories.class));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Category updated successfully", savedCategory));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/categories/{category-id}")
    public ResponseEntity<GeneralResponse<?>> deleteCategory(@PathVariable("category-id") long categoryId)
    {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Category deleted successfully", null));
    }


}
