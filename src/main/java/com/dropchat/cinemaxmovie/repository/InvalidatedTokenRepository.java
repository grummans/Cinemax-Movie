package com.dropchat.cinemaxmovie.repository;

import com.dropchat.cinemaxmovie.entity.InvalidateToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidateToken, String> {
}
