package com.dropchat.cinemaxmovie.entity;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BillStatus")
public class BillStatus {

    // define field for table "BillStatus"
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "billStatus")
    @JsonManagedReference("bill-billStatus")
    private List<Bill> bills;

    // define constructor without property id
    public BillStatus(String name, List<Bill> bills) {
        this.name = name;
        this.bills = bills;
    }
}
