package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.entity.RefreshToken;
import com.dropchat.cinemaxmovie.exception.ApplicationException;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.RefreshTokenRepository;
import com.dropchat.cinemaxmovie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)))
                .token(UUID.randomUUID().toString())
                .expiredTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

}
