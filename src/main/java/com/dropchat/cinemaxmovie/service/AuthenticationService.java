package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.converter.request.AuthenticationRequest;
import com.dropchat.cinemaxmovie.converter.response.ApplicationException;
import com.dropchat.cinemaxmovie.converter.response.MessageResponse;
import com.dropchat.cinemaxmovie.entity.ConfirmEmail;
import com.dropchat.cinemaxmovie.entity.UserStatus;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.ConfirmEmailRepository;
import com.dropchat.cinemaxmovie.repository.UserRepository;
import com.dropchat.cinemaxmovie.repository.UserStatusRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConfirmEmailRepository confirmEmailRepository;
    private final UserStatusRepository userStatusRepository;

    public boolean authenticatedUser(@NotNull AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        return encoder.matches(request.getPassword(), user.getPassword());
    }

    public MessageResponse verifyEmail(String otp){

        //Find email by OTP Code
        var currentEmail = confirmEmailRepository.findByConfirmCode(otp)
                .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND));
        //Check if email is verified or not
        if(currentEmail.isConfirm()){
            return new MessageResponse("Email is already verified");
        }else if(currentEmail.getExpiredTime().compareTo(new Date()) > 0){
            currentEmail.setConfirm(true);
            currentEmail.getUser().setActive(true);
            currentEmail.getUser().setUserStatus(userStatusRepository.findNameByCode("True")
                    .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)));
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Email verified");
        }else {
            currentEmail.setConfirmCode(currentEmail.getUser().getEmail());
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Expired Time! Please regenerate otp and try again");
        }
    }
}
