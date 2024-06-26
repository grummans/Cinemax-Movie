package com.dropchat.cinemaxmovie.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Table(name = "InvalidateToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidateToken {

    @Id
    private String id;

    @Column(name = "expiryTime")
    private Date expiryTime;
}
