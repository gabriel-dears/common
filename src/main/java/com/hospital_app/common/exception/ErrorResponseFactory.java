package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Set;

public class ErrorResponseFactory {

    public static ErrorResponse createUnauthorizedErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.UNAUTHORIZED);
    }

    public static ErrorResponse createNotFoundErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.NOT_FOUND);
    }

    public static ErrorResponse createInternalServerErrorErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus status, Set<String> errors) {
        return new ErrorResponse(
                errors != null ? errors : Set.of(exception.getMessage()),
                Instant.now().toString(),
                status.value(),
                request.getRequestURI()
        );
    }

    private static ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus status) {
        return getErrorResponse(exception, request, status, null);
    }

    public static ErrorResponse createBadRequestErrorResponse(Exception exception, HttpServletRequest request, Set<String> errors) {
        return getErrorResponse(exception, request, HttpStatus.BAD_REQUEST, errors);
    }
}
