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
@Table(name = "SeatStatus")
public class SeatStatus {

    //define field for table "SeatStatus"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "nameStatus")
    private String nameStatus;

    @OneToMany(mappedBy = "seatStatus")
    @JsonManagedReference("seat-seatStatus")
    private List<Seat> seats;

}
