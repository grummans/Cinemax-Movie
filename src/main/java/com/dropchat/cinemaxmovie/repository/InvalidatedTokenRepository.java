package com.dropchat.cinemaxmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dropchat.cinemaxmovie.entity.InvalidateToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidateToken, String> {}
