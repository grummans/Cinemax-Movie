package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.SeatStatus;
import com.dropchat.cinemaxmovie.entity.enums.ESeatStatus;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Integer> {
    Optional<SeatStatus> findByNameStatus(ESeatStatus name);
}
