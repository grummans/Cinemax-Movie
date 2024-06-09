package com.dropchat.cinemaxmovie.entity;

import java.time.LocalDate;
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
@Table(name = "Promotion")
public class Promotion {

    // define field for table "Promotion"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "percent")
    private int percent;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "type")
    private String type;

    @Column(name = "startTime")
    private LocalDate startTime;

    @Column(name = "endTime", columnDefinition = "DATE")
    private LocalDate endTime;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "promotion")
    @JsonManagedReference("bill-promotion")
    private List<Bill> bills;

    @ManyToOne
    @JoinColumn(name = "rankCustomerId")
    @JsonBackReference("promotion-rankCustomer")
    private RankCustomer rankCustomer;
}
