package com.dropchat.cinemaxmovie.util;

import com.dropchat.cinemaxmovie.entity.ConfirmEmail;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.repository.ConfirmEmailRepository;
import com.dropchat.cinemaxmovie.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    @Value("${application.security.jwt.email-verify.expiration}")
    private long emailExpiration;
    private final EmailService emailService;
    private final ConfirmEmailRepository confirmEmailRepository;

    /***
     * Generate a random 6 numbers OTP to verify email
     * @return random OTP 6 numbers
     */
    public String generateOTP(){
        Random random = new Random();
        int randomNumber = random.nextInt(999999); //Random 6 numbers OTP
        String otp = String.valueOf(randomNumber); //convert to String
        while (otp.length()<6){
            otp = "0" + otp;
        }
        return otp;
    }

    public void sendEmail(String email, String otp) {
        String subject = "Email verification";
        String body = "Your verification otp is " + otp;
        emailService.sendOTPEmail(email, subject, body);
    }

    /**
     * Method create a verification email
     * @param user -
     * @return
     */
    public ConfirmEmail createConfirmEmail(User user){

        ConfirmEmail confirmEmail = new ConfirmEmail();
        confirmEmail.setUser(user);

        //Format requiredTime
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); //Format time "HH:mm:ss"
        String formattedExpriration = format.format(new Date()); //convert Time to String
        confirmEmail.setRequiredTime(formattedExpriration);

        confirmEmail.setExpiredTime(new Date(System.currentTimeMillis()  + emailExpiration));
        confirmEmail.setConfirmCode(generateOTP());
        sendEmail(user.getEmail(), confirmEmail.getConfirmCode());
        confirmEmailRepository.save(confirmEmail);
        return confirmEmail;
    }

}
