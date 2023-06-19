package com.goodday.proj.api.member.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.member.service.MemberService;
import com.goodday.proj.api.member.dto.LoginFormDto;
import com.goodday.proj.api.member.dto.RegisterFormDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String test() {
        return "ok";
    }

    /**
     * 아이디로 회원정보 모두 조회 후 비밀번호 일치하면 return Member
     * 불일치 시 400error
     */
    @PostMapping("/login")
    public Member login(@Valid @RequestBody LoginFormDto login, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.loginError);
        }

        Member member = memberService.login(login);

        return member;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterFormDto registerUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.registerError);
        }

        int register = memberService.register(registerUser);

//        if (register == 0) {
//            throw new RuntimeException(ErrorConst.registerError);
//        }

        if (register > 0) {
            log.info("[{}]님이 회원가입 하셨습니다.", registerUser.getId());
            return "ok";
        }

        return "fail";
    }
}
