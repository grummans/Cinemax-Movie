package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.RankCustomer;

@Repository
public interface RankCustomerRepository extends JpaRepository<RankCustomer, Integer> {

    Optional<RankCustomer> findByName(String name);

    Optional<RankCustomer> findRankNameByPoint(Integer integer);
}
