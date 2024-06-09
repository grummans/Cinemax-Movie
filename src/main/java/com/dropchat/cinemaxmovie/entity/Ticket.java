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
@Table(name = "Ticket")
public class Ticket {

    // define field for table "Ticket"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "priceTicket")
    private double priceTicket;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "ticket")
    @JsonManagedReference("billTicket-ticket")
    private List<BillTicket> billTickets;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    @JsonBackReference("ticket-schedule")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "seatId")
    @JsonBackReference("ticket-seat")
    private Seat seat;
}
