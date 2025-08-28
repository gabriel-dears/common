package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base class for global exception handling across applications.
 * <p>
 * Provides common methods to transform exceptions into {@link ErrorResponse}
 * objects wrapped in {@link ResponseEntity}, ensuring consistent error responses
 * across projects that include this module as a dependency.
 * <p>
 * This class is designed to be extended by application-specific global exception
 * handlers, which can override or build upon the provided behavior.
 */
@SuppressWarnings("unused") // Used externally in projects consuming this common module
public abstract class GlobalExceptionHandlerBase {

    /**
     * Handles exceptions that represent "not found" errors (e.g., missing resources or entities).
     * <p>
     * This method delegates to {@link ErrorResponseEntityFactory#getNotFound(Exception, HttpServletRequest)}
     * to create a standardized error response.
     *
     * @param exception the exception that occurred
     * @param request   the current HTTP request
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse}
     */
    @SuppressWarnings("unused") // Used externally in projects consuming this common module
    public ResponseEntity<ErrorResponse> handleInputNotFoundErrors(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getNotFound(exception, request);
    }

    /**
     * Handles custom input-related errors (e.g., validation or domain-specific input exceptions).
     * <p>
     * This method delegates to {@link ErrorResponseEntityFactory#getBadRequest(Exception, HttpServletRequest)}
     * to create a standardized error response.
     *
     * @param exception the exception that occurred
     * @param request   the current HTTP request
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse}
     */
    @SuppressWarnings("unused") // Used externally in projects consuming this common module
    public ResponseEntity<ErrorResponse> handleCustomInputErrors(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getBadRequest(exception, request);
    }

    /**
     * Handles validation errors triggered by {@link MethodArgumentNotValidException}.
     * <p>
     * Collects validation error messages for invalid fields and creates a
     * {@link ResponseEntity} containing the {@link ErrorResponse}.
     *
     * @param exception the validation exception
     * @param request   the current HTTP request
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse} with validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        Set<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toSet());
        return ErrorResponseEntityFactory.getBadRequest(exception, request, errors);
    }

    /**
     * Handles generic uncaught exceptions that are not explicitly mapped to specific handlers.
     * <p>
     * This method ensures that unexpected errors are returned as standardized
     * internal server error responses.
     *
     * @param exception the unhandled exception
     * @param request   the current HTTP request
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getInternalServerError(exception, request);
    }

}
