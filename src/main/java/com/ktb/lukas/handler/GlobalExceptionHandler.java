package com.ktb.lukas.handler;

import com.ktb.lukas.exception.BusinessException;
import com.ktb.lukas.exception.NotFoundException;
import com.ktb.lukas.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            NotFoundException exception) {

        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponse.of(exception.getCode(), null));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(
            BusinessException exception) {

        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponse.of(exception.getCode(), null));
    }
}