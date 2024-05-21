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
@Table(name = "Rate")
public class Rate {

    //define field for table "Rate"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "rate")
    private List<Movie> movies;

    //define constructor without property id
    public Rate(String description, String code, List<Movie> movies) {
        this.description = description;
        this.code = code;
        this.movies = movies;
    }
}
