package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Schedule;
import com.dropchat.cinemaxmovie.repository.RoomRepository;
import com.dropchat.cinemaxmovie.repository.ScheduleRepository;
import com.dropchat.cinemaxmovie.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;
    private final ApplicationConfig config;

    public Schedule addNew(Schedule newSchedule) {
        return scheduleRepository.save(newSchedule);
    }

    public Schedule remake(Schedule remakeSchedule) {
        var current = scheduleRepository.findById(remakeSchedule.getId())
                .orElseThrow(() -> new RuntimeException("Data not found"));
//        if (checkTimeConflict(remakeSchedule)) return null;
        BeanUtils.copyProperties(remakeSchedule, current, config.getNullPropertyNames(remakeSchedule));
        return scheduleRepository.save(current);
    }

    public boolean checkTimeConflict(Schedule checkSchedule) {
        return roomRepository.findAll().stream()
                .flatMap(room -> room.getSchedules().stream())
                .anyMatch(schedule ->
                        schedule.getRoom().equals(checkSchedule.getRoom())
                                && schedule.getMovie().equals(checkSchedule.getMovie())
                );
    }

    public Schedule delete(String code) {
        var current = scheduleRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Data not found"));
        ticketRepository.findAll().forEach(x -> {
            if (x.getSchedule().getCode().equals(code))
                x.setActive(false);
        });
        current.setActive(false);
        return current;
    }

}
