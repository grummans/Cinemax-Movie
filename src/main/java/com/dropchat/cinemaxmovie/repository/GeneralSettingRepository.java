package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.GeneralSetting;

@Repository
public interface GeneralSettingRepository extends JpaRepository<GeneralSetting, Integer> {}
