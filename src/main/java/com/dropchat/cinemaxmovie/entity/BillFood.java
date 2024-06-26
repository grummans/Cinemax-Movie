package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BillFood")
@Data
@NoArgsConstructor
public class BillFood {

    // define field for table "BillFood"
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "billId")
    @JsonBackReference("billFood-bill")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "foodId")
    @JsonBackReference("billFood-food")
    private Food food;

    // define constructor without property id
    public BillFood(int quantity, Bill bill, Food food) {
        this.quantity = quantity;
        this.bill = bill;
        this.food = food;
    }
}
