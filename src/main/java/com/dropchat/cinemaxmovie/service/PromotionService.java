package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.entity.Promotion;
import com.dropchat.cinemaxmovie.repository.BillRepository;
import com.dropchat.cinemaxmovie.repository.PromotionRepository;
import com.dropchat.cinemaxmovie.repository.RankCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final RankCustomerRepository rankCustomerRepository;
    private final PromotionRepository promotionRepository;
    private final BillRepository billRepository;
    private final ApplicationConfig config;

    public Promotion addNew(Promotion newPromotion) {
        promotionRepository.save(newPromotion);
        var current = promotionRepository.findByType(newPromotion.getType())
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        rankCustomerRepository.findAll().forEach(rank -> rank.getPromotions().forEach(x -> {
            if (x.getRankCustomer().equals(current.getRankCustomer()) && !rank.isActive()) {
                promotionRepository.delete(current);
                newPromotion.setName("delete");
            }
        }));
        return newPromotion;
    }

    public Promotion remake(Promotion remakePromotion) {
        var current = promotionRepository.findById((int) remakePromotion.getId())
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        if (remakePromotion.getPercent() == 0) remakePromotion.setPercent(current.getPercent());
        if (remakePromotion.getQuantity() == 0) remakePromotion.setQuantity(current.getQuantity());
        BeanUtils.copyProperties(remakePromotion, current, config.getNullPropertyNames(remakePromotion));
        return promotionRepository.save(current);
    }

    public Promotion delete(String type) {
        var current = promotionRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        billRepository.findAll().forEach(x -> {
            if (x.getPromotion().equals(current)) x.setActive(false);
        });
        current.setActive(false);
        return promotionRepository.save(current);
    }
}
