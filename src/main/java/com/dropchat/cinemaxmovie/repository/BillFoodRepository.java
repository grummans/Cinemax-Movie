package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Bill;
import com.dropchat.cinemaxmovie.entity.BillFood;
import com.dropchat.cinemaxmovie.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFood, Integer> {
    Optional<BillFood> findBillFoodByBillsAndFood(Bill bill, Food food);
}
