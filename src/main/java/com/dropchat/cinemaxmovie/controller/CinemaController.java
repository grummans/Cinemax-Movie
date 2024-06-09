package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Cinema;
import com.dropchat.cinemaxmovie.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Cinema request) {
        return ResponseEntity.ok(cinemaService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Cinema request) {
        return ResponseEntity.ok(cinemaService.remake(request));
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String cinemaName) {
        return ResponseEntity.ok(cinemaService.delete(cinemaName));
    }

}
