package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Ticket;
import com.dropchat.cinemaxmovie.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Ticket request) {
        return ResponseEntity.ok(ticketService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Ticket request) {
        return ResponseEntity.ok(ticketService.remake(request));
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {
        return ResponseEntity.ok(ticketService.delete(code));
    }

}
