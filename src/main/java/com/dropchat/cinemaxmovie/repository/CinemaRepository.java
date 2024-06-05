package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    Optional<Cinema> findByNameOfCinema(String name);
}
