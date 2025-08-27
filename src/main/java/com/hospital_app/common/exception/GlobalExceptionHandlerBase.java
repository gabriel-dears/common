package com.hospital_app.common.exception;

import com.hospital_app.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class GlobalExceptionHandlerBase {

    public ResponseEntity<ErrorResponse> handleInputNotFoundErrors(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getNotFound(exception, request);
    }

    public ResponseEntity<ErrorResponse> handleCustomInputErrors(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getBadRequest(exception, request);
    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request) {
        return ErrorResponseEntityFactory.getInternalServerError(exception, request);
    }

}
