package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.UserStatus;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

    Optional<UserStatus> findByCode(String code);

    Optional<UserStatus> findUserStatusByCode(String code);

    Optional<UserStatus> findNameByCode(String code);
}
