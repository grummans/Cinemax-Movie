package com.dropchat.cinemaxmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Room;
import com.dropchat.cinemaxmovie.repository.CinemaRepository;
import com.dropchat.cinemaxmovie.repository.RoomRepository;
import com.dropchat.cinemaxmovie.repository.ScheduleRepository;
import com.dropchat.cinemaxmovie.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;
    private final ApplicationConfig config;

    private boolean checkDataExist() {
        return (cinemaRepository.count() > 0);
    }

    public Room addNew(Room newRoom) {
        if (!checkDataExist()) return null;
        return roomRepository.save(newRoom);
    }

    public Room remake(Room remakeRoom) {
        var current =
                roomRepository.findById(remakeRoom.getId()).orElseThrow(() -> new RuntimeException("Data not found !"));
        BeanUtils.copyProperties(remakeRoom, current, config.getNullPropertyNames(remakeRoom));
        return roomRepository.save(current);
    }

    public Room delete(String code) {
        var current = roomRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Data not found !"));
        scheduleRepository.findAll().forEach(x -> {
            if (x.getRoom().getName().equals(code)) x.setRoom(null);
        });
        seatRepository.findAll().forEach(x -> {
            if (x.getRoom().getName().equals(code)) x.setRoom(null);
        });
        roomRepository.delete(current);
        return current;
    }
}
