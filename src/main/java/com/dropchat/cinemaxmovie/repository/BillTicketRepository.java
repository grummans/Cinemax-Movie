package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Bill;
import com.dropchat.cinemaxmovie.entity.BillTicket;
import com.dropchat.cinemaxmovie.entity.Ticket;

@Repository
public interface BillTicketRepository extends JpaRepository<BillTicket, Integer> {
    Optional<BillTicket> findBillTicketByBillAndTicket(Bill bill, Ticket ticket);
}
