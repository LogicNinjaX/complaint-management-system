package com.cms.complaint_management_system.controller;


import com.cms.complaint_management_system.dto.api_response.ErrorResponse;
import com.cms.complaint_management_system.exception.DepartmentNotFoundException;
import com.cms.complaint_management_system.exception.OfficerException;
import com.cms.complaint_management_system.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({UserNotFoundException.class, DepartmentNotFoundException.class})
    public ResponseEntity<ErrorResponse<?>> userExceptionHandler(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(false, e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
    }


    @ExceptionHandler(OfficerException.class)
    public ResponseEntity<ErrorResponse<?>> officerExceptionHandler(OfficerException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse<String> error = new ErrorResponse<>(false, "invalid input", HttpStatus.BAD_REQUEST.value(), errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleGenericException(Exception ex) {
        ErrorResponse<?> error = new ErrorResponse<>(false, "Something went wrong: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }



}
