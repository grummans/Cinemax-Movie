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
@Table(name = "Cinema")
public class Cinema {

    //define field for table "Cinema"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "nameOfCinema")
    private String nameOfCinema;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "cinema")
    private List<Room> rooms;

    //define constructor without property id
    public Cinema(String address, String description, String code, String nameOfCinema, boolean isActive) {
        this.address = address;
        this.description = description;
        this.code = code;
        this.nameOfCinema = nameOfCinema;
        this.isActive = isActive;
    }
}
