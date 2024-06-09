package com.dropchat.cinemaxmovie.entity;

import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

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
