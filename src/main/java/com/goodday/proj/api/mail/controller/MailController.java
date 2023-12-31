package com.goodday.proj.api.mail.controller;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.mail.service.MailService;
import com.goodday.proj.api.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final MemberRepository memberRepository;

    @GetMapping("/register")
    public CompletableFuture<String> registerAuthentication(@RequestParam String email) throws MessagingException {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }

        if (memberRepository.findSessionMemberByEmail(email).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        return mailService.registerAuthentication(email);
    }

    @GetMapping("/find/id")
    public CompletableFuture<String> findIdAuthentication(@RequestParam String email) throws MessagingException {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }
        return mailService.findAuthentication(email, "아이디");
    }

    @GetMapping("/find/password")
    public CompletableFuture<String> findPwdAuthentication(@RequestParam String email) throws MessagingException {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }
        return mailService.findAuthentication(email, "비밀번호");
    }

}
