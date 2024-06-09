package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Seat;
import com.dropchat.cinemaxmovie.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Seat request) {
        return ResponseEntity.ok((seatService.addNew(request)));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Seat request) {
        return ResponseEntity.ok(seatService.remake(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok(seatService.delete(id));
    }

}
