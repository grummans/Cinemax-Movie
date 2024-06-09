package com.dropchat.cinemaxmovie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be between 3 and 20 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be between 6 and 40 characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1005, "Email format is invalid", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1006, "PhoneNumber format is invalid", HttpStatus.BAD_REQUEST),
    USER_INVALID(1007, "Username or password is incorrect. Please try again", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1008, "Username not found", HttpStatus.NOT_FOUND),
    USER_NOT_VERIFY(1009, "User is not verify. Please verify your account", HttpStatus.BAD_REQUEST),
    USER_UNAUTHENTICATED(4001, "User is unauthenticated", HttpStatus.UNAUTHORIZED),
    DATA_NOT_FOUND(4004, "Data not found", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_ERROR(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(8888, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(4002, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
