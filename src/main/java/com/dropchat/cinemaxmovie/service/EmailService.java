package com.dropchat.cinemaxmovie.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service("emailService")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;

    /**
     * Method sendOtpEmail for sending OTP to register
     * @param title Title of email
     * @param subject Subject of email
     * @param body Content of email
     * @return
     **/
    public void sendOTPEmail(String title, String subject, String body){

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(title);
            helper.setSubject(subject);
            helper.setText(body);

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
