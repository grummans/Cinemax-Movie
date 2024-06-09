package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Schedule;
import com.dropchat.cinemaxmovie.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Schedule request) {
        return ResponseEntity.ok(scheduleService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Schedule request) {
        return ResponseEntity.ok(scheduleService.remake(request));
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {
        return ResponseEntity.ok(scheduleService.delete(code));
    }

}
