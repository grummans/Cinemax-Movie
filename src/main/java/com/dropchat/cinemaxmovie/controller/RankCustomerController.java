package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.RankCustomer;
import com.dropchat.cinemaxmovie.service.RankCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rank-customer")
public class RankCustomerController {

    private final RankCustomerService rankCustomerService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody RankCustomer request) {
        return ResponseEntity.ok(rankCustomerService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody RankCustomer request) {
        return ResponseEntity.ok(rankCustomerService.remake(request));
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        return ResponseEntity.ok(rankCustomerService.delete(name));
    }

}
