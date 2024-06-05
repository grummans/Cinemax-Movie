package com.dropchat.cinemaxmovie.repository;


import com.dropchat.cinemaxmovie.entity.SeatStatus;
import com.dropchat.cinemaxmovie.entity.enums.ESeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Integer> {
    Optional<SeatStatus> findByNameStatus(ESeatStatus name);
}
