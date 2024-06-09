package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.BillTicket;
import com.dropchat.cinemaxmovie.service.BillTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-ticket")
public class BillTicketController {

    private final BillTicketService billTicketService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody BillTicket request) {
        return ResponseEntity.ok(billTicketService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody BillTicket request) {
        return ResponseEntity.ok(billTicketService.remake(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok(billTicketService.delete(id));
    }

}
