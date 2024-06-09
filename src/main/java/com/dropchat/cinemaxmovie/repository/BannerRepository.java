package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {}
