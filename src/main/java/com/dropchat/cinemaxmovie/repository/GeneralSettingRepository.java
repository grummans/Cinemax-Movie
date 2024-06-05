package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.GeneralSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralSettingRepository extends JpaRepository<GeneralSetting, Integer> {

}
