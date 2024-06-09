package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.converter.request.BillRequest;
import com.dropchat.cinemaxmovie.service.BillService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody BillRequest newRequest,
                                    HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        return ResponseEntity.ok(billService.addNew(newRequest, baseUrl));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody BillRequest remakeRequest,
                                    HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        return ResponseEntity.ok(billService.remake(remakeRequest, baseUrl));
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        return ResponseEntity.ok(billService.delete(name));
    }

}
