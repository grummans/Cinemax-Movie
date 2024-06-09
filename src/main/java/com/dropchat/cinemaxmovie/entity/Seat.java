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
@Table(name = "Seat")
public class Seat {

    // define field for table "Seat"
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
    @JsonManagedReference("ticket-seat")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "seatStatusId")
    @JsonBackReference("seat-seatStatus")
    private SeatStatus seatStatus;

    @ManyToOne
    @JoinColumn(name = "roomId")
    @JsonBackReference("seat-room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "seatTypeId")
    @JsonBackReference("seat-seatType")
    private SeatType seatType;
}
