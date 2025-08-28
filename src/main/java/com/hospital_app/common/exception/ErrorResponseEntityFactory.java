package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

/**
 * Factory class for creating {@link ResponseEntity} objects containing standardized
 * {@link ErrorResponse} instances.
 * <p>
 * Provides convenient static methods to generate HTTP responses for common error
 * scenarios, such as 400 Bad Request, 401 Unauthorized, 404 Not Found, and 500
 * Internal Server Error. Delegates to {@link ErrorResponseFactory} to create the
 * actual error payload.
 */
public class ErrorResponseEntityFactory {

    /**
     * Creates a {@link ResponseEntity} with HTTP 404 Not Found status.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a {@link ResponseEntity} containing a {@link ErrorResponse} with status 404
     */
    public static ResponseEntity<ErrorResponse> getNotFound(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseFactory.createNotFoundErrorResponse(exception, request));
    }

    /**
     * Creates a {@link ResponseEntity} with HTTP 400 Bad Request status without detailed errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a {@link ResponseEntity} containing a {@link ErrorResponse} with status 400
     */
    public static ResponseEntity<ErrorResponse> getBadRequest(Exception exception, HttpServletRequest request) {
        return getBadRequest(exception, request, null);
    }

    /**
     * Creates a {@link ResponseEntity} with HTTP 500 Internal Server Error status.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a {@link ResponseEntity} containing a {@link ErrorResponse} with status 500
     */
    public static ResponseEntity<ErrorResponse> getInternalServerError(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseFactory.createInternalServerErrorErrorResponse(exception, request));
    }

    /**
     * Creates a {@link ResponseEntity} with HTTP 401 Unauthorized status.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a {@link ResponseEntity} containing a {@link ErrorResponse} with status 401
     */
    @SuppressWarnings("unused") // Used externally in projects consuming this common module
    public static ResponseEntity<ErrorResponse> getUnauthorized(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseFactory.createUnauthorizedErrorResponse(exception, request));
    }

    /**
     * Creates a {@link ResponseEntity} with HTTP 400 Bad Request status and optional detailed errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @param errors    a set of detailed error messages; can be null
     * @return a {@link ResponseEntity} containing a {@link ErrorResponse} with status 400
     */
    public static ResponseEntity<ErrorResponse> getBadRequest(Exception exception, HttpServletRequest request, Set<String> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFactory.createBadRequestErrorResponse(exception, request, errors));
    }
}
