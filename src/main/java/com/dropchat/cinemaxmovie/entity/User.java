package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "User")
public class User {

    //define field for table "User"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "point")
    private int point;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;

    @OneToMany(mappedBy = "user")
    private List<ConfirmEmail> confirmEmails;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    @ManyToOne
    @JoinColumn(name = "userStatusId")
    private UserStatus userStatus;

    @ManyToOne
    @JoinColumn(name = "rankCustomerId")
    private RankCustomer rankCustomer;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
