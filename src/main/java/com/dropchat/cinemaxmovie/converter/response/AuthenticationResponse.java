package com.dropchat.cinemaxmovie.converter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponse {
    private String authenticate;
}
