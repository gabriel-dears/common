package com.hospital_app.common.exception.dto;

import java.util.Set;

/**
 * Represents a standardized error response payload for HTTP API responses.
 * <p>
 * This record encapsulates error messages, the HTTP status code, the request path,
 * and a timestamp indicating when the error occurred.
 *
 * <p>Typically used by {@link com.hospital_app.common.exception.ErrorResponseFactory}
 * and {@link com.hospital_app.common.exception.ErrorResponseEntityFactory} to
 * provide consistent error responses across different services.
 *
 * @param messages  a set of error messages describing the failure
 * @param timestamp the timestamp (ISO-8601 format) when the error occurred
 * @param status    the HTTP status code associated with the error
 * @param path      the request URI that triggered the error
 */
public record ErrorResponse(
        Set<String> messages,
        String timestamp,
        int status,
        String path
) {
}
