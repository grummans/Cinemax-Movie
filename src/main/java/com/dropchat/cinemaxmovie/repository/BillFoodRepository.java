package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Bill;
import com.dropchat.cinemaxmovie.entity.BillFood;
import com.dropchat.cinemaxmovie.entity.Food;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFood, Integer> {
    Optional<BillFood> findBillFoodByBillAndFood(Bill bill, Food food);
}
