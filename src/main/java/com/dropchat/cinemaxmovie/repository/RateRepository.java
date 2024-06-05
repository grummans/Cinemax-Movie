package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
}
