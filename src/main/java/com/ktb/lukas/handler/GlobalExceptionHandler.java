package com.ktb.lukas.handler;
import com.ktb.lukas.Api.ApiResponse;
import com.ktb.lukas.exception.CustomException;
import com.ktb.lukas.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        // CustomException 에서 enum(ErrorCode)를 꺼낸다.
        ErrorCode errorCode = e.getErrorCode();
        // status -> errorCode의 status, // body -> errorCode
        return ResponseEntity.status(errorCode.getStatus()) .body(ApiResponse.error(errorCode));
    }

    // 예기치 못한 서버 에러 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}