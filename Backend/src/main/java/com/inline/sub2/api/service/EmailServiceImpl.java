package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

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

    @Override
    public void sendPassword(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String TemporaryPassword = getRamdomPassword();
        //임시 비밀번호 발송
        message.setTo(email);
        message.setSubject("Inline 임시 비밀번호 발급 안내");
        message.setText("안녕하세요 " + email + "님 임시 비밀번호 안내드립니다. 비밀번호 변경 후 사용하세요\n" + "임시 비밀번호 : " + TemporaryPassword);

        javaMailSender.send(message);
    }

    public String getRamdomPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;

        for (int i = 0; i < 12; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

}
