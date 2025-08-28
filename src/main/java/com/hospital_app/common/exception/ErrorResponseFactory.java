package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Set;

/**
 * Factory class for creating standardized {@link ErrorResponse} objects.
 * <p>
 * Provides convenient static methods to generate error responses for common HTTP
 * status codes such as 400 (Bad Request), 401 (Unauthorized), 404 (Not Found),
 * and 500 (Internal Server Error). The factory ensures consistent formatting of
 * error messages, timestamps, HTTP status codes, and request URIs.
 */
public class ErrorResponseFactory {

    /**
     * Creates an {@link ErrorResponse} for HTTP 401 Unauthorized errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a populated {@link ErrorResponse} with status 401
     */
    public static ErrorResponse createUnauthorizedErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Creates an {@link ErrorResponse} for HTTP 404 Not Found errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a populated {@link ErrorResponse} with status 404
     */
    public static ErrorResponse createNotFoundErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates an {@link ErrorResponse} for HTTP 500 Internal Server errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @return a populated {@link ErrorResponse} with status 500
     */
    public static ErrorResponse createInternalServerErrorErrorResponse(Exception exception, HttpServletRequest request) {
        return getErrorResponse(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Creates an {@link ErrorResponse} for HTTP 400 Bad Request errors.
     * <p>
     * Allows specifying a set of validation or custom error messages.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @param errors    a set of detailed error messages
     * @return a populated {@link ErrorResponse} with status 400
     */
    public static ErrorResponse createBadRequestErrorResponse(Exception exception, HttpServletRequest request, Set<String> errors) {
        return getErrorResponse(exception, request, HttpStatus.BAD_REQUEST, errors);
    }

    /**
     * Internal helper method to create an {@link ErrorResponse} with optional detailed errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @param status    the HTTP status to set in the response
     * @param errors    optional set of detailed error messages; if null, the exception message is used
     * @return a populated {@link ErrorResponse}
     */
    private static ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus status, Set<String> errors) {
        return new ErrorResponse(
                errors != null ? errors : Set.of(exception.getMessage()),
                Instant.now().toString(),
                status.value(),
                request.getRequestURI()
        );
    }

    /**
     * Internal helper method to create an {@link ErrorResponse} without detailed errors.
     *
     * @param exception the exception that triggered the error
     * @param request   the HTTP request where the error occurred
     * @param status    the HTTP status to set in the response
     * @return a populated {@link ErrorResponse}
     */
    private static ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus status) {
        return getErrorResponse(exception, request, status, null);
    }
}
