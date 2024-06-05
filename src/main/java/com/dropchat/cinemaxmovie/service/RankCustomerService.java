package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.RankCustomer;
import com.dropchat.cinemaxmovie.repository.PromotionRepository;
import com.dropchat.cinemaxmovie.repository.RankCustomerRepository;
import com.dropchat.cinemaxmovie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankCustomerService {

    private final RankCustomerRepository rankRepo;
    private final UserRepository userRepository;
    private final PromotionRepository proRepo;
    private final ApplicationConfig config;

    public RankCustomer addNew(RankCustomer newRequest) {
        return rankRepo.save(newRequest);
    }

    public RankCustomer remake(RankCustomer remakeRequest) {
        var current = rankRepo.findById(remakeRequest.getId())
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        if (remakeRequest.getPoint() == 0) remakeRequest.setPoint(current.getPoint());
        BeanUtils.copyProperties(remakeRequest, current, config.getNullPropertyNames(remakeRequest));
        return rankRepo.save(current);
    }

    public RankCustomer delete(String name) {
        var current = rankRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        userRepository.findAll().forEach(x -> {
            if (x.getRankCustomer().equals(current)) x.setRankCustomer(null);
        });
        proRepo.findAll().forEach(x -> {
            if (x.getRankCustomer().equals(current)) x.setActive(false);
        });
        current.setActive(false);
        return rankRepo.save(current);
    }

}
