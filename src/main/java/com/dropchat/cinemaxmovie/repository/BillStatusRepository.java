package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.BillStatus;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus, Integer> {}
