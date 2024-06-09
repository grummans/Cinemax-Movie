package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByCode(String code);
}
