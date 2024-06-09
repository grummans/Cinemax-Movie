package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Food;
import com.dropchat.cinemaxmovie.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {
    private final FoodService foodService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Food request) {
        return ResponseEntity.ok(foodService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Food request) {
        return ResponseEntity.ok(foodService.remake(request));
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String name) {
        return ResponseEntity.ok(foodService.delete(name));
    }

    @GetMapping()
    public ResponseEntity<?> getFoodFeature() {
        return ResponseEntity.ok(foodService.sortFoodByQuantity());
    }

}
