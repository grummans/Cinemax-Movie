package com.dropchat.cinemaxmovie.entity;

import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ConfirmEmail")
public class ConfirmEmail {

    // define field for table "ConfirmEmail"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "requiredTime")
    private String requiredTime;

    @Column(name = "expiredTime")
    private Date expiredTime;

    @Column(name = "confirmCode")
    private String confirmCode;

    @Column(name = "isConfirm")
    private boolean isConfirm;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference("confirmEmail-user")
    private User user;
}
