package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByTradingCode(String name);
}
