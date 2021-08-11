package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.EmailDto;
import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.OnBoardEntity;
import com.inline.sub2.db.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Override
    public void sendEmail(UserRegistDto user) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            //구성원에게 이메일 발송
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Welcome to Inline!");
            messageHelper.setText("<html><body><div class=\"main\">\n" +
                    "    <div class=\"logo\">\n" +
                    "      <span>인-라인</span>\n" +
                    "    </div>\n" +
                    "    <div class=\"msg\">\n" +
                    "      <span>" + user.getOfficeName() + "에서 구성원 등록 메일을 보냈습니다.</span><br>\n" +
                    "      <span>" + user.getName() +"님으로 구성원 등록을 진행하시겠습니까?</span>\n" +
                    "      <br><br>\n" +
                    "      <div class=\"button-list\">\n" +
                    "        <button type=\"button\" onclick=\"location.href = 'https://i5d207.p.ssafy.io/onboarding/signup?email='" + user.getEmail() +"\">인라인 시작하기</button>\n" +
                    "      </div>\n" +
                    "      <br>\n" +
                    "      <hr>\n" +
                    "      <div class=\"footer\">\n" +
                    "        <span>해당 메일은 <a href=\"#\">인-라인</a>에서 발송되었습니다.</span>\n" +
                    "        <br>\n" +
                    "        <span>© 2021 인라인, All Rights Reserved</span>\n" +
                    "    </div>\n" +
                    "    </div></body></html>", true);

            javaMailSender.send(message);
            log.info("가입 메일 발송 성공 :{}");
        }catch(MessagingException e){
            log.error("가입 메일 발송 실패 :{}", e);
        }
    }

    @Override
    public String sendPassword(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String TemporaryPassword = getRamdomPassword();
        UserEntity userEntity = userService.getUserByEmail(email);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email);
            messageHelper.setSubject("Inline 임시 비밀번호 발급 안내");
            messageHelper.setText(  "<html><body><div style=\"display: flex; flex-direction: column;\"> \n" +
                   " <h1>인-라인</h1>\n" +
                "<h2>비밀번호를 잊으셨나요?</h2>\n" +
                "<p>" + userEntity.getName() + "님, 안녕하세요. 임시 비밀번호를 발급해 드렸습니다.<br>" +
                    "아래 임시 비밀번호를 통해 로그인 해 주시고, 로그인 후 비밀번호를 꼭 바꿔주시기 바랍니다.</p>\n" +
                "<p style=\"font-weight: bold;\"><span>임시 비밀번호 : </span>"+TemporaryPassword+"</p>\n" +
                    "</div></body></html>", true);

            javaMailSender.send(message);
        }catch(MessagingException e){
            log.error("가입 메일 발송 실패 :{}", e);
        }
        //임시 비밀번호 발송

        return TemporaryPassword;
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
