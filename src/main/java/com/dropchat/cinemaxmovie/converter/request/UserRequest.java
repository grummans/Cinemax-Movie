package com.dropchat.cinemaxmovie.converter.request;

import com.dropchat.cinemaxmovie.exception.ErrorCode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    @Pattern(regexp = "^(.+)@(\\S+)$", message = "EMIL_INVALID")
    private String email;

    @Size(min = 1, max = 50, message = "Name invalid")
    private String name;

    @Pattern(regexp = "0[0-9]{9}", message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
}
