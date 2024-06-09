package com.dropchat.cinemaxmovie.converter.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {

    private String token;
}
