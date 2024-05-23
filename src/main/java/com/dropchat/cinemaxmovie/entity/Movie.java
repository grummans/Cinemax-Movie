package com.dropchat.cinemaxmovie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    //define field for table "Movie"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "movieDuration")
    private int movieDuration;

    @Column(name = "endTime")
    private LocalDate endTime;

    @Column(name = "premiereDate")
    private LocalDate premiereDate;

    @Column(name = "description")
    private String description;

    @Column(name = "director")
    private String director;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "heroImage")
    private String heroImage;

    @Column(name = "language")
    private String language;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference("schedule-movie")
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "movieTypeId")
    @JsonBackReference("movie-movieType")
    private MovieType movieType;

    @ManyToOne
    @JoinColumn(name = "rateId")
    @JsonBackReference
    private Rate rate;



}
