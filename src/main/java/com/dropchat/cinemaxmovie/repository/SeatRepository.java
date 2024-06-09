package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {}
