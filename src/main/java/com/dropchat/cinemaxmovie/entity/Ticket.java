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
@Table(name = "Ticket")
public class Ticket {

    //define field for table "Ticket"
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

    @OneToMany(mappedBy = "ticketId")
    private List<BillTicket> billTickets;

    @OneToMany(mappedBy = "schedulesId")
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "seatId")
    private Seat seat;

}
