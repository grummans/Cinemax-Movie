package com.dropchat.cinemaxmovie.service;

import java.text.ParseException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.exception.ApplicationException;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.UserRepository;
import com.dropchat.cinemaxmovie.repository.UserStatusRepository;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader =
                request.getHeader("Authorization"); // Returns the value of the specified request header as a String
        final String jwt;
        if (authHeader == null || authHeader.startsWith("Bearer")) {
            return;
        }
        jwt = authHeader.substring(7);
        log.warn(jwt);
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            String username = signedJWT.getJWTClaimsSet().getSubject();
            var user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
            log.warn(String.valueOf(user));
            user.setUserStatus(userStatusRepository
                    .findByCode("False")
                    .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)));
            userRepository.save(user);
            SecurityContextHolder.clearContext(); // Explicitly clears the context value from the current thread.

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
