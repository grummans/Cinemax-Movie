package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
}
