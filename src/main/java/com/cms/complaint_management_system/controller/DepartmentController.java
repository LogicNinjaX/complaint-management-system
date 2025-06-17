package com.cms.complaint_management_system.controller;

import com.cms.complaint_management_system.dto.api_request.DepartmentRegisterRequest;
import com.cms.complaint_management_system.dto.api_response.GeneralResponse;
import com.cms.complaint_management_system.entity.Departments;
import com.cms.complaint_management_system.service.DepartmentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    public DepartmentController(DepartmentService departmentService, ModelMapper modelMapper) {
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/departments")
    public ResponseEntity<GeneralResponse<Departments>> saveDepartment(@Valid @RequestBody DepartmentRegisterRequest request){

        var savedDepartment = departmentService.saveDepartment(modelMapper.map(request, Departments.class));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Department saved successfully", savedDepartment));
    }

    @GetMapping("/departments")
    public ResponseEntity<GeneralResponse<List<Departments>>> getDepartments(
            @RequestParam(defaultValue = "1", required = false, name = "page") int pageNumber,
            @RequestParam(defaultValue = "10", required = false, name = "size") int pageSize,
            @RequestParam(defaultValue = "departmentName", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sortDir

    )
    {
        var departmentList = departmentService.getDepartmentList(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Departments fetched successfully", departmentList));
    }

    @GetMapping("/departments/{department-id}")
    public ResponseEntity<GeneralResponse<Departments>> getDepartmentById(@PathVariable("department-id") long departmentId){
        var department = departmentService.getDepartmentById(departmentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Department fetched successfully", department));
    }


    @DeleteMapping("/departments/{department-id}")
    public ResponseEntity<GeneralResponse<?>> deleteDepartmentById(@PathVariable("department-id") long departmentId){
        departmentService.deleteDepartmentById(departmentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse<>(true, "Department deleted successfully", null));
    }


}




