package com.dropchat.cinemaxmovie.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Schedule")
public class Schedule {

    // define field for table "Schedule"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "startAt")
    private LocalDate startAt;

    @Column(name = "endAt")
    private LocalDate endAt;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "schedule")
    @JsonManagedReference("ticket-schedule")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "movieId")
    @JsonBackReference("schedule-movie")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "roomId")
    @JsonBackReference("schedule-room")
    private Room room;

    // define constructor without property id
    public Schedule(double price, LocalDate startAt, LocalDate endAt, String code, String name, boolean isActive) {
        this.price = price;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
        this.name = name;
        this.isActive = isActive;
    }
}
