package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {

    Optional<ConfirmEmail> findByUserId(Integer id);

    Optional<ConfirmEmail> findByConfirmCode(String otp);
}
