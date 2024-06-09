package com.dropchat.cinemaxmovie.service;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.converter.response.SortFoodByQuantity;
import com.dropchat.cinemaxmovie.entity.Food;
import com.dropchat.cinemaxmovie.repository.BillFoodRepository;
import com.dropchat.cinemaxmovie.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final BillFoodRepository billFoodRepository;
    private final ApplicationConfig config;

    public Food addNew(Food newFood) {
        return foodRepository.save(newFood);
    }

    public Food remake(Food remakeFood) {
        var current =
                foodRepository.findById(remakeFood.getId()).orElseThrow(() -> new RuntimeException("Data not found !"));
        BeanUtils.copyProperties(remakeFood, current, config.getNullPropertyNames(remakeFood));
        return foodRepository.save(current);
    }

    public Food delete(String name) {
        var current = foodRepository.findByNameOfFood(name).orElseThrow(() -> new RuntimeException("Data not found !"));
        billFoodRepository.findAll().forEach(x -> {
            if (x.getFood().getNameOfFood().equals(name)) x.setFood(null);
        });
        foodRepository.delete(current);
        return current;
    }

    public List<SortFoodByQuantity> sortFoodByQuantity() {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Subtract 7 days (7 * 24 * 60 * 60 * 1000 milliseconds) from the current time
        long sevenDaysAgoMillis = currentTimeMillis - (7L * 24L * 60L * 60L * 1000L);

        // Create a new Date object representing the date 7 days ago
        Date sevenDaysAgo = new Date(sevenDaysAgoMillis);

        //

        Map<String, SortFoodByQuantity> foodMap = new HashMap<>();

        billFoodRepository.findAll().forEach(x -> {
            var name = x.getFood().getNameOfFood();
            int quantity = x.getQuantity();
            var date = x.getBill().getUpdateTime();
            if (date.compareTo(sevenDaysAgo) >= 0) {
                foodMap.compute(name, (key, existingItem) -> {
                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                        return existingItem;
                    } else {
                        return new SortFoodByQuantity(x.getId(), name, quantity);
                    }
                });
            }
        });

        List<SortFoodByQuantity> obj = new ArrayList<>(foodMap.values());

        return obj.stream()
                .sorted(Comparator.comparingInt(SortFoodByQuantity::getQuantity).reversed())
                .toList();
    }
}
