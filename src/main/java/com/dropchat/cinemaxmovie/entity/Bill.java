package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Bill")
public class Bill {

    //define field for table "Bill"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "totalMoney")
    private double totalMoney;

    @Column(name = "tradingCode")
    private String tradingCode;

    @Column(name = "createTime")
    private LocalDate createTime;

    @OneToMany(mappedBy = "bill")
    private List<BillTicket> billTickets;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "id")
    private String name;

    @Column(name = "id")
    private LocalDate updateTime;

    @Column(name = "id")
    private Promotion promotion;

    @Column(name = "id")
    private BillStatus billStatus;

    @Column(name = "id")
    private boolean isActive;

}
