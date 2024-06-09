package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Promotion;
import com.dropchat.cinemaxmovie.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/promotion")
    public ResponseEntity<?> addNew(@RequestBody Promotion request) {
        return ResponseEntity.ok(promotionService.addNew(request));
    }

    @PutMapping("/promotion")
    public ResponseEntity<?> remake(@RequestBody Promotion request) {
        return ResponseEntity.ok(promotionService.remake(request));
    }

    @PutMapping("/promotion/{type}")
    public ResponseEntity<?> delete(@PathVariable String type) {
        return ResponseEntity.ok(promotionService.delete(type));
    }

}
