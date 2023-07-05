package com.goodday.proj.api.member.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.dto.*;
import com.goodday.proj.api.member.repository.MemberRepository;
//import com.goodday.proj.api.member.service.GoogleLoginService;
import com.goodday.proj.api.member.service.KakaoLoginService;
import com.goodday.proj.api.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final KakaoLoginService kakaoLoginService;
//    private final GoogleLoginService googleLoginService;

    /**
     * 로그인
     * @param login
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public MemberSessionInfo login(@Valid @RequestBody LoginFormDto login, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.loginError);
        }

        return memberService.login(login);
    }

    /**
     * 회원가입
     * @param registerUser
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterFormDto registerUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !registerUser.getEmail().contains("@")) {
            throw new IllegalArgumentException(ErrorConst.registerError);
        }

        int register = memberService.register(registerUser);

        if (register > 0) {
            log.info("[{}({})]님이 회원가입 하셨습니다.", registerUser.getNickname(), registerUser.getId());
            return "ok";
        }

        return "fail";
    }

    /**
     * 아이디찾기
     * @param email
     * @return
     */
    @PostMapping("/find/id")
    public String findId(@RequestParam String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }

        Optional<MemberSessionInfo> member = memberRepository.findSessionMemberByEmail(email);

        MemberSessionInfo m = member.filter(presentMember -> member.isPresent())
                .orElseThrow(() -> new IllegalArgumentException(ErrorConst.findError));

        return m.getMemberId();
    }

    /**
     * 비밀번호찾기
     * @param email
     * @return
     */
    @PostMapping("/find/password")
    public String findPwd(@RequestParam String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(ErrorConst.mailError);
        }

        Optional<MemberSessionInfo> member = memberRepository.findSessionMemberByEmail(email);

        MemberSessionInfo m = member.filter(presentMember -> member.isPresent())
                .orElseThrow(() -> new IllegalArgumentException(ErrorConst.findError));

        return memberRepository.findPwdById(m.getMemberId());
    }

    /**
     * 회원탈퇴
     * @param memberNo
     */
    @DeleteMapping("/{memberNo}")
    public void withdrawalMember(@PathVariable Long memberNo) {
        log.info("{}({}) 님이 회원탈퇴 하셨습니다.", memberRepository.findSessionMemberByNo(memberNo).get().getNickname(),
                memberRepository.findSessionMemberByNo(memberNo).get().getMemberId());
        memberRepository.deleteMember(memberNo);
    }

    /**
     * 아이디중복체크
     * @param id
     * @return
     */
    @GetMapping("/check/id")
    public String checkId(@RequestBody Map<String, String> id) {
        Optional<MemberSessionInfo> checkId = memberRepository.findSessionMemberById(id.get("id"));
        if (checkId.isPresent()) {
            return "fail";
        }
        return "ok";
    }

    /**
     * 카카오로그인
     * @param code
     * @return
     * @throws IOException
     */
    @GetMapping("/kakao")
    public MemberSessionInfo  kakaoCallback(@RequestParam String code) throws IOException {
        String kakaoAccessToken = kakaoLoginService.getKakaoAccessToken(code);
        var kakaoMemberInfo = kakaoLoginService.getKakaoMemberInfo(kakaoAccessToken);

        if (memberRepository.findSessionMemberById(kakaoMemberInfo.get("id").toString()).isEmpty()) {
            log.info("[{}({})]님이 회원가입 하셨습니다.", kakaoMemberInfo.get("nickname").toString(),
                    kakaoMemberInfo.get("id").toString());
            memberRepository.saveKakaoMember(kakaoMemberInfo);
        }

        return memberRepository.findSessionMemberById(kakaoMemberInfo.get("id").toString()).get();
    }

//    @PostMapping("/google")
//    public void googleCallBack(@RequestParam String code) {
//        log.debug("code : {}", code);
//        googleLoginService.getGoogleAccessToken(code);
//    }

}
