package com.dropchat.cinemaxmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.BillTicket;
import com.dropchat.cinemaxmovie.repository.BillTicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillTicketService {

    private final BillTicketRepository billTicketRepository;
    private final ApplicationConfig config;

    public BillTicket addNew(BillTicket newBillTicket) {
        return billTicketRepository.save(newBillTicket);
    }

    public BillTicket remake(BillTicket remakeBillTicket) {
        var current = billTicketRepository
                .findById(remakeBillTicket.getId())
                .orElseThrow(() -> new RuntimeException("Data not found"));
        BeanUtils.copyProperties(remakeBillTicket, current, config.getNullPropertyNames(remakeBillTicket));
        return billTicketRepository.save(current);
    }

    public BillTicket delete(int id) {
        var current = billTicketRepository.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        billTicketRepository.delete(current);
        return current;
    }
}
