package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByCode(String code);
}
