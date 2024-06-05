package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
