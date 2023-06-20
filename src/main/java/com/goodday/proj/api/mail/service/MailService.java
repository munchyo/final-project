package com.goodday.proj.api.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Value(value = "${spring.mail.username}")
    private String setFrom;

    private final JavaMailSenderImpl mailSender;

    public int makeRandomNumber() {
        Random r = new Random();
        return r.nextInt(888888) + 111111;
    }

    public void sendMail(String setFrom, String setTo, String title, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom(setFrom);
        helper.setTo(setTo);
        helper.setSubject(title);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public String registerAuthentication(String email) throws MessagingException {
        int authNumber = makeRandomNumber();
        String setTo = email;
        String title = "GoodDay에 오신것을 환영합니다!";
        String content =
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 확인란에 기입해주세요.";
        sendMail(setFrom, setTo, title, content);
        return Integer.toString(authNumber);
    }

    public String findAuthentication(String email, String find) throws MessagingException {
        int authNumber = makeRandomNumber();
        String setTo = email;
        String title = "GoodDay " + find + " 찾기 인증메일 입니다.";
        String content =
                "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 확인란에 기입해주세요.";
        sendMail(setFrom, setTo, title, content);
        return Integer.toString(authNumber);
    }

}