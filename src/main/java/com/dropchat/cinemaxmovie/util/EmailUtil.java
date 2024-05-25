package com.dropchat.cinemaxmovie.util;

import com.dropchat.cinemaxmovie.entity.ConfirmEmail;
import com.dropchat.cinemaxmovie.entity.User;
import com.dropchat.cinemaxmovie.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class EmailUtil {

    private EmailService emailService;

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
        String body = "your verification otp is" + otp;
        emailService.sendOTPEmail(email, subject, body);
    }

    public void createConfirmEmail(User user){

        ConfirmEmail confirmEmail = new ConfirmEmail();
        confirmEmail.setUser(user);
        //Format requiredTime
        Date exiprationDate = new Date(100);
    }

}
