package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByTradingCode(String name);
}
