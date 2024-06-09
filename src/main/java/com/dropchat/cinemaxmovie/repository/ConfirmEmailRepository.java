package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.entity.ConfirmEmail;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {

    Optional<ConfirmEmail> findByUserId(Integer id);

    Optional<ConfirmEmail> findByConfirmCode(String otp);
}
