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
@Table(name = "BillStatus")
public class BillStatus {

    //define field for table "BillStatus"
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "billStatus")
    private List<Bill> bills;

    //define constructor without property id
    public BillStatus(String name, List<Bill> bills) {
        this.name = name;
        this.bills = bills;
    }
}
