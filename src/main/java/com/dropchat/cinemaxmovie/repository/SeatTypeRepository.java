package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.SeatType;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {}
