package com.dropchat.cinemaxmovie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SeatType")
public class SeatType {

    //define field for table "SeatType"
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
