package com.dropchat.cinemaxmovie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RefreshToken")
@Builder
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiredTime")
    private Date expiredTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference("refreshToken-user")
    private User user;

}
