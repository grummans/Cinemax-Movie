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

    @Column(name = "name")
    private String name;

    @Column(name = "updateTime")
    private LocalDate updateTime;

    @ManyToOne
    @JoinColumn(name = "promotionId")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "billStatusId")
    private BillStatus billStatus;

    @Column(name = "isActive")
    private boolean isActive;

    //define constructor without property id
    public Bill(double totalMoney, String tradingCode, LocalDate createTime, String name,
                LocalDate updateTime, boolean isActive) {
        this.totalMoney = totalMoney;
        this.tradingCode = tradingCode;
        this.createTime = createTime;
        this.name = name;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }
}
