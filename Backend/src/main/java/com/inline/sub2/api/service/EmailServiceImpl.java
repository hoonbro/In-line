package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        //구성원에게 이메일 발송
        message.setTo(email);
        message.setSubject("Welcome to Inline!");
        message.setText("https://13.124.47.223:8083/");

        javaMailSender.send(message);
    }
}
