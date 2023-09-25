package com.geybriyel.music.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {

    INVALID_REQUEST(400, "Invalid request"),
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error. An error occurred while retrieving the data"),
    JSON_PARSE_ERROR(500, "Error parsing the JSON Object");


    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
