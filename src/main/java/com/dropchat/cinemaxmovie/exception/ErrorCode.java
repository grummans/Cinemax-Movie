package com.dropchat.cinemaxmovie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Username must be between 3 and 20 characters"),
    PASSWORD_INVALID(1004, "Password must be between 6 and 40 characters"),
    EMAIL_INVALID(1005, "Email format is invalid"),
    PHONE_NUMBER_INVALID(1006, "PhoneNumber format is invalid"),
    USER_INVALID(1007, "Username or password is incorrect. Please try again"),
    USER_NOT_FOUND(1008, "Username not found"),
    USER_NOT_VERIFY(1009, "User is not verify. Please verify your account"),
    DATA_NOT_FOUND(4004, "Data not found"),
    UNCATEGORIZED_ERROR(9999, "Uncategorized exception"),
    INVALID_KEY(8888, "Uncategorized exception"),
    ;
    private int code;
    private String message;
}
