package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {
}
