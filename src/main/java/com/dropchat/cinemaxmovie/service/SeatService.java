package com.dropchat.cinemaxmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Seat;
import com.dropchat.cinemaxmovie.repository.SeatRepository;
import com.dropchat.cinemaxmovie.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ApplicationConfig config;

    public Seat addNew(Seat newSeat) {
        return seatRepository.save(newSeat);
    }

    public Seat remake(Seat remakeSeat) {
        var current =
                seatRepository.findById(remakeSeat.getId()).orElseThrow(() -> new RuntimeException("Data not found"));
        BeanUtils.copyProperties(remakeSeat, current, config.getNullPropertyNames(remakeSeat));
        return seatRepository.save(current);
    }

    public Seat delete(int id) {
        var current = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        ticketRepository.findAll().forEach(x -> {
            if (Integer.valueOf(id).equals(x.getSeat().getId())) x.setSeat(null);
        });
        seatRepository.delete(current);
        return current;
    }
}
