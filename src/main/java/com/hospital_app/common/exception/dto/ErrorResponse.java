package com.hospital_app.common.exception.dto;

import java.util.Set;

public record ErrorResponse(
        Set<String> messages,
        String timestamp,
        int status,
        String path
) {
}
