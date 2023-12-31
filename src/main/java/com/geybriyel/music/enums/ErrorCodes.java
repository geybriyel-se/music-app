package com.geybriyel.music.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCodes {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Resource not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error. An error occurred while retrieving the data"),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST.value(), "Error parsing the JSON object"),
    INVALID_SONG_ID(HttpStatus.BAD_REQUEST.value(), "Invalid song ID"),
    USERNAME_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Username is already taken"),
    INCORRECT_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "The provided username or password is incorrect"),
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Email address is already in use by another user"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), "Invalid email address format"),
    USER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "User does not exist"),
    FOLDER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "This folder already exists"),
    INVALID_FOLDER_ID(HttpStatus.BAD_REQUEST.value(), "Invalid folder id"),
    FOLDER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "You don't have this folder"),
    SONG_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "Song already exists in the folder"),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST.value(), "Invalid user id"),
    ;


    private final int code;
    private final String message;
}
