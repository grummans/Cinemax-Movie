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
@Table(name = "SeatType")
public class SeatType {

    // define field for table "SeatType"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nameType")
    private String nameType;

    @OneToMany(mappedBy = "seatType")
    @JsonManagedReference("seat-seatType")
    private List<Seat> seats;
}
