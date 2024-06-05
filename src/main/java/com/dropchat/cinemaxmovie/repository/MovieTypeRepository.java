package com.dropchat.cinemaxmovie.repository;


import com.dropchat.cinemaxmovie.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Integer> {
}
