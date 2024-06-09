package com.dropchat.cinemaxmovie.entity;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MovieType")
public class MovieType {

    // define field for table "MovieType"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "movieTypeName")
    private String movieTypeName;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "movieType")
    @JsonManagedReference("movie-movieType")
    private List<Movie> movies;

    // define constructor without property id
    public MovieType(String movieTypeName, boolean isActive, List<Movie> movies) {
        this.movieTypeName = movieTypeName;
        this.isActive = isActive;
        this.movies = movies;
    }
}
