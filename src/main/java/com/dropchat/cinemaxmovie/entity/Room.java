package com.dropchat.cinemaxmovie.entity;

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
@Table(name = "Room")
public class Room {

    // define field for table "Room"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference("schedule-room")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference("seat-room")
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "cinemaId")
    @JsonBackReference("room-cinema")
    private Cinema cinema;
}
