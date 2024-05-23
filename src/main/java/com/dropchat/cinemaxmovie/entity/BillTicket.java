package com.dropchat.cinemaxmovie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BillTicket")
public class BillTicket {

    //define field for table "BillTicket"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "billId")
    @JsonBackReference("billTicket-bill")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    @JsonBackReference("billTicket-ticket")
    private Ticket ticket;

    public BillTicket(int quantity, Bill bill, Ticket ticket) {
        this.quantity = quantity;
        this.bill = bill;
        this.ticket = ticket;
    }
}
