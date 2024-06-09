package com.dropchat.cinemaxmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.BillFood;
import com.dropchat.cinemaxmovie.repository.BillFoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillFoodService {

    private final BillFoodRepository billFoodRepository;
    private final ApplicationConfig config;

    public BillFood addNew(BillFood newBillFood) {
        return billFoodRepository.save(newBillFood);
    }

    public BillFood remake(BillFood remakeBilLFood) {
        var current = billFoodRepository
                .findById(remakeBilLFood.getId())
                .orElseThrow(() -> new RuntimeException("Data not found"));
        BeanUtils.copyProperties(remakeBilLFood, current, config.getNullPropertyNames(remakeBilLFood));
        return billFoodRepository.save(current);
    }

    public BillFood delete(int id) {
        var current = billFoodRepository.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        billFoodRepository.delete(current);
        return current;
    }
}
