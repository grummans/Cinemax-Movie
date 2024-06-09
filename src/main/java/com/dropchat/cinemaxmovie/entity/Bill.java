package com.dropchat.cinemaxmovie.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Bill")
public class Bill {

    // define field for table "Bill"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "totalMoney")
    private double totalMoney;

    @Column(name = "tradingCode")
    private String tradingCode;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "name")
    private String name;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "bill")
    @JsonManagedReference("billTicket-bill")
    private List<BillTicket> billTickets;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference("bill-user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "promotionId")
    @JsonBackReference("bill-promotion")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "billStatusId")
    @JsonBackReference("bill-billStatus")
    private BillStatus billStatus;

    // define constructor without property id
    public Bill(
            double totalMoney, String tradingCode, Date createTime, String name, Date updateTime, boolean isActive) {
        this.totalMoney = totalMoney;
        this.tradingCode = tradingCode;
        this.createTime = createTime;
        this.name = name;
        this.updateTime = updateTime;
        this.isActive = isActive;
    }
}
