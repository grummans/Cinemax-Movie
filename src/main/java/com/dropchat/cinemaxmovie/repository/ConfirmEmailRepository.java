package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {
}
