package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public class ErrorResponseEntityFactory {

    public static ResponseEntity<ErrorResponse> getNotFound(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseFactory.createNotFoundErrorResponse(exception, request));
    }

    public static ResponseEntity<ErrorResponse> getBadRequest(Exception exception, HttpServletRequest request) {
        return getBadRequest(exception, request, null);
    }

    public static ResponseEntity<ErrorResponse> getInternalServerError(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseFactory.createInternalServerErrorErrorResponse(exception, request));
    }

    public static ResponseEntity<ErrorResponse> getUnauthorized(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponseFactory.createUnauthorizedErrorResponse(exception, request));
    }

    public static ResponseEntity<ErrorResponse> getBadRequest(Exception exception, HttpServletRequest request, Set<String> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseFactory.createBadRequestErrorResponse(exception, request, errors));
    }
}
