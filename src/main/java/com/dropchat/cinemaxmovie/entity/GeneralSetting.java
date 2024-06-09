package com.dropchat.cinemaxmovie.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GeneralSetting")
@NoArgsConstructor
public class GeneralSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "breakTime")
    private LocalDate breakTime;

    @Column(name = "businessHours")
    private int businessHours;

    @Column(name = "closeTime")
    private LocalDate closeTime;

    @Column(name = "fixedTicketPrice")
    private double fixedTicketPrice;

    @Column(name = "percentDay")
    private int percentDay;

    @Column(name = "percentWeekend")
    private int percentWeekend;

    @Column(name = "timeBeginToChange")
    private LocalDate timeBeginToChange;

    // define constructor without property id;
    public GeneralSetting(
            LocalDate breakTime,
            int businessHours,
            LocalDate closeTime,
            double fixedTicketPrice,
            int percentDay,
            int percentWeekend,
            LocalDate timeBeginToChange) {
        this.breakTime = breakTime;
        this.businessHours = businessHours;
        this.closeTime = closeTime;
        this.fixedTicketPrice = fixedTicketPrice;
        this.percentDay = percentDay;
        this.percentWeekend = percentWeekend;
        this.timeBeginToChange = timeBeginToChange;
    }
}
