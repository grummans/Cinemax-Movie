package com.dropchat.cinemaxmovie.converter.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
