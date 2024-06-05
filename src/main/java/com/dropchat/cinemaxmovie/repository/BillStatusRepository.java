package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus, Integer> {
}
