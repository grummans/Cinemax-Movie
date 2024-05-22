package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ConfirmEmail")
public class ConfirmEmail {

    //define field for table "ConfirmEmail"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "requiredTime")
    private LocalDate requiredTime;

    @Column(name = "expiredTime")
    private LocalDate expiredTime;

    @Column(name = "confirmCode")
    private String confirmCode;

    @Column(name = "isConfirm")
    private boolean isConfirm;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
