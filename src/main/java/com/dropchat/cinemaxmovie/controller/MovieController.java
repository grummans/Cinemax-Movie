package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.entity.Movie;
import com.dropchat.cinemaxmovie.entity.enums.ESeatStatus;
import com.dropchat.cinemaxmovie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping()
    public ResponseEntity<?> addNew(@RequestBody Movie request) {
        return ResponseEntity.ok(movieService.addNew(request));
    }

    @PutMapping()
    public ResponseEntity<?> remake(@RequestBody Movie request) {
        return ResponseEntity.ok(movieService.remake(request));
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String name) {
        return ResponseEntity.ok(movieService.delete(name));
    }

    @GetMapping("/getByCinema/{page}/{size}/{name}")
    public ResponseEntity<?> getMovieByCinema(
            @PathVariable String name,
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageAble = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.getMovieByCinema(pageAble, name));
    }

    @GetMapping("/getByRoom/{page}/{size}/{code}")
    public ResponseEntity<?> getMovieByRoom(
            @PathVariable String code,
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageAble = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.getMovieByRoom(pageAble, code));
    }

    @GetMapping("/getBySeatStatus/{page}/{size}/{name}")
    public ResponseEntity<?> getMovieBySeatStatus(
            @PathVariable ESeatStatus name,
            @PathVariable int page,
            @PathVariable int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.getMovieBySeatStatus(pageable, name));
    }

    @GetMapping("/sortMovieByTicket")
    public ResponseEntity<?> sortMovieByTicket() {
        return ResponseEntity.ok(movieService.sortMovieByTicket());
    }

}
