package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Bill;
import com.dropchat.cinemaxmovie.entity.BillTicket;
import com.dropchat.cinemaxmovie.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillTicketRepository extends JpaRepository<BillTicket, Integer> {
    Optional<BillTicket> findBillTicketByBillAndTicket(Bill bill, Ticket ticket);
}
