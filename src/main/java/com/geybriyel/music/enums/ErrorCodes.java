package com.geybriyel.music.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCodes {

    INVALID_REQUEST(400, "Invalid request"),
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error. An error occurred while retrieving the data"),
    JSON_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error parsing the JSON Object"),
    INVALID_SONG_ID(HttpStatus.BAD_REQUEST.value(), "Invalid song ID"),
    USERNAME_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Username is already taken.")
    ;


    private final int code;
    private final String message;
}
