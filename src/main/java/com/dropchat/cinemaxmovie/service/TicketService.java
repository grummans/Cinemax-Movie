package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Ticket;
import com.dropchat.cinemaxmovie.repository.BillTicketRepository;
import com.dropchat.cinemaxmovie.repository.ScheduleRepository;
import com.dropchat.cinemaxmovie.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;
    private final BillTicketRepository billTicketRepository;
    private final ApplicationConfig config;

    private String generateCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    public Ticket addNew(Ticket newTicket) {
        newTicket.setCode(generateCode());
        ticketRepository.save(newTicket);
        var current = ticketRepository.findByCode(newTicket.getCode())
                .orElseThrow(() -> new RuntimeException("Date not found !"));
        scheduleRepository.findAll().forEach(schedule -> schedule.getTickets().forEach(x -> {
            if (current.getSchedule().equals(x.getSchedule()))
                current.setPriceTicket(schedule.getPrice());
        }));
        // Check Seat and Schedule Active before create
        ticketRepository.save(current);
        return current;
    }

    public Ticket remake(Ticket remakeTicket) {
        var current = ticketRepository.findById(remakeTicket.getId())
                .orElseThrow(() -> new RuntimeException("Date not found !"));
        if ((int) remakeTicket.getPriceTicket() == 0)
            remakeTicket.setPriceTicket(current.getPriceTicket());
        BeanUtils.copyProperties(remakeTicket, current, config.getNullPropertyNames(remakeTicket));
        return ticketRepository.save(current);
    }

    public Ticket delete(String code) {
        var current = ticketRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Date not found !"));
        billTicketRepository.findAll().forEach(x -> {
            if (x.getTicket().getCode().equals(code))
                billTicketRepository.delete(x);
        });
        current.setActive(false);
        return ticketRepository.save(current);
    }
}
