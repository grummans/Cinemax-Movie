package com.dropchat.cinemaxmovie.converter.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequest {
    private String token;
}
