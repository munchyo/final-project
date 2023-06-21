package com.goodday.proj.api.member.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.dto.*;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public MemberSessionInfo login(@Valid @RequestBody LoginFormDto login, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.loginError);
        }

        return memberService.login(login);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterFormDto registerUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !registerUser.getEmail().contains("@")) {
            log.debug("bindingResult [{}], {}", bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            throw new IllegalArgumentException(ErrorConst.registerError);
        }

        int register = memberService.register(registerUser);

        if (register > 0) {
            log.info("[{}]님이 회원가입 하셨습니다.", registerUser.getId());
            return "ok";
        }

        return "fail";
    }

    @PostMapping("/find/id")
    public String findId(@RequestParam String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }

        Optional<MemberSessionInfo> member = memberRepository.findByEmail(email);

        MemberSessionInfo m = member.filter(presentMember -> member.isPresent())
                .orElseThrow(() -> new IllegalArgumentException(ErrorConst.findError));

        return m.getMemberId();
    }

    @PostMapping("/find/password")
    public String findPwd(@RequestParam String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }

        Optional<MemberSessionInfo> member = memberRepository.findByEmail(email);

        MemberSessionInfo m = member.filter(presentMember -> member.isPresent())
                .orElseThrow(() -> new IllegalArgumentException(ErrorConst.findError));

        return memberRepository.findPwdById(m.getMemberId());
    }

    @PostMapping("/edit/{memberNo}")
    public Member viewInfo(@PathVariable Long memberNo) throws Exception {
        log.debug("memberNo {}", memberNo);
        Optional<Member> member = memberRepository.findMemberByNo(memberNo);
        log.debug("member [{}]", member.get());
        return member.filter(presentMember -> member.isPresent()).orElseThrow(Exception::new);
    }

    // TODO password 변경 검증
    @PutMapping("/edit/{memberNo}/password")
    public String editPwd(@PathVariable Long memberNo, @Valid EditPwdDto pwdDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }

        int result = memberService.editPwd(memberNo, pwdDto);

        return null;
    }

    @PutMapping("/edit/{memberNo}/nickname")
    public String editNickname(@PathVariable Long memberNo, EditNicknameDto nicknameDto) {
        return null;
    }

    @PutMapping("/edit/{memberNo}/name")
    public String editName(@PathVariable Long memberNo, EditNameDto nameDto) {
        return null;
    }

    @PutMapping("/edit/{memberNo}/phone")
    public String editPhone(@PathVariable Long memberNo, EditPhoneDto phoneDto) {
        return null;
    }
}
