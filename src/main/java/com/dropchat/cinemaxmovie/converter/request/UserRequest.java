package com.dropchat.cinemaxmovie.converter.request;

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

    @Min(value = 0, message = "Value must be greater than or equal to 0")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    private int point;

    @Size(min = 3, message = "Error code")
    private String username;

    @Pattern(regexp = "^(.+)@(\\\\S+)$", message = "Email invalid")
    private String email;

    @Size(min = 1, max = 50, message = "Name invalid")
    private String name;

    @Pattern(regexp = "0[0-9]{9}", message = "Phone number invalid")
    private String phoneNumber;

    @Size(min = 8, message = "Password invalid")
    private String password;
}
