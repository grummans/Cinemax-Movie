package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Cinema;
import com.dropchat.cinemaxmovie.repository.CinemaRepository;
import com.dropchat.cinemaxmovie.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;
    private final ApplicationConfig config;


    public Cinema addNew(Cinema newCinema) {
        return cinemaRepository.save(newCinema);
    }


    public Cinema remake(Cinema remakeCinema) {
        var currentCinema = cinemaRepository.findById(remakeCinema.getId())
                .orElseThrow(() -> new RuntimeException("Cinema request is not in database !"));
        // copy value except null
        BeanUtils.copyProperties(remakeCinema, currentCinema, config.getNullPropertyNames(remakeCinema));
        return cinemaRepository.save(currentCinema);
    }


    public Cinema delete(String name) {
        var delCinema = cinemaRepository.findByNameOfCinema(name)
                .orElseThrow(() -> new RuntimeException("Cinema request is not in database !"));
        roomRepository.findAll().forEach(x -> {
            if (x.getCinema().getNameOfCinema().equals(name))
                x.setCinema(null);
        });
        cinemaRepository.delete(delCinema);
        return delCinema;
    }

}
