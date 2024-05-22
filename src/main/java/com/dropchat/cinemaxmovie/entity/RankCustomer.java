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
@Table(name = "RankCustomer")
public class RankCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "point")
    private int point;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "rankCustomer")
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "rankCustomer")
    private List<User> users;

}
