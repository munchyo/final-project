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

import java.util.Optional;

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
    public Member viewInfo(@PathVariable Long memberNo, @RequestParam String pwd) throws Exception {
        log.debug("memberNo {}", memberNo);
        Optional<Member> member = memberRepository.findMemberByNo(memberNo);
        log.debug("member [{}]", member.get());
        return member.filter(presentMember -> member.isPresent()).orElseThrow(Exception::new);
    }

    @PutMapping("/edit/password/{memberNo}")
    public String editPwd(@PathVariable Long memberNo, EditPwdDto pwd) {
        return null;
    }

    @PutMapping("/edit/nickname/{memberNo}")
    public String editNickname(@PathVariable Long memberNo, EditNicknameDto nickname) {
        return null;
    }

    @PutMapping("/edit/name/{memberNo}")
    public String editName(@PathVariable Long memberNo, EditNameDto name) {
        return null;
    }

    @PutMapping("/edit/phone/{memberNo}")
    public String editPhone(@PathVariable Long memberNo, EditPhoneDto phone) {
        return null;
    }
}
