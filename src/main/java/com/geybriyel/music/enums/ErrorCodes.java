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
    USERNAME_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Username is already taken"),
    INCORRECT_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "The provided username or password is incorrect"),
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Email address is already in use by another user"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), "Invalid email address format"),
    USER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "User does not exist"),
    ;


    private final int code;
    private final String message;
}
