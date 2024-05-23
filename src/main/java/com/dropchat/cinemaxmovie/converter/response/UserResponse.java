package com.dropchat.cinemaxmovie.converter.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    int id;
    private int point;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private String rank;

}
