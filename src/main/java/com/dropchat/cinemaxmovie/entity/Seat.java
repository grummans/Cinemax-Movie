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
@Table(name = "Seat")
public class Seat {

    //define field for table "Seat"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private int number;

    @Column(name = "line")
    private String line;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "seatStatusId")
    private SeatStatus seatStatus;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "seatTypeId")
    private SeatType seatType;

}
