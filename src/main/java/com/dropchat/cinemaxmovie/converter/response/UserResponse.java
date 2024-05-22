package com.dropchat.cinemaxmovie.converter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private int point;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private String roleName;
    private String rankName;

}
