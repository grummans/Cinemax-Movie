package com.dropchat.cinemaxmovie.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Banner")
public class Banner {

    // define field for table "Banner"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "title")
    private String title;
}
