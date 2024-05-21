package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Food")
@Getter
@Setter
@NoArgsConstructor
public class Food {

    //define field for table "Food"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "id")
    private String nameOfFood;

    @Column(name = "id")
    private boolean isActive;

    @OneToMany(mappedBy = "food")
    private List<BillFood> billFoods;

    //define constructor without property id
    public Food(double price, String description, String image, String nameOfFood, boolean isActive, List<BillFood> billFoods) {
        this.price = price;
        this.description = description;
        this.image = image;
        this.nameOfFood = nameOfFood;
        this.isActive = isActive;
        this.billFoods = billFoods;
    }
}
