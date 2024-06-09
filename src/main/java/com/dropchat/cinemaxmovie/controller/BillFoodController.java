package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.BillFood;
import com.dropchat.cinemaxmovie.service.BillFoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-food")
public class BillFoodController {

    private final BillFoodService billFoodService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody BillFood request) {
        return ResponseEntity.ok(billFoodService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody BillFood request) {
        return ResponseEntity.ok(billFoodService.remake(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok(billFoodService.delete(id));
    }

}
