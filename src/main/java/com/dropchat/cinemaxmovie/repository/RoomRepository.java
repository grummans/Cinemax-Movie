package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByCode(String code);
}
