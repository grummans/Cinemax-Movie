package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Room;
import com.dropchat.cinemaxmovie.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Room request) {
        return ResponseEntity.ok(roomService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Room request) {
        return ResponseEntity.ok(roomService.remake(request));
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String roomCode) {
        return ResponseEntity.ok(roomService.delete(roomCode));
    }

}
