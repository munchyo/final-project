package com.goodday.proj.api.member.controller;

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
@RequestMapping("/member/edit")
public class MemberEditController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/{memberNo}")
    public Member viewInfo(@PathVariable Long memberNo) throws Exception {
        Optional<Member> member = memberRepository.findMemberAndAddressByNo(memberNo);
        return member.filter(presentMember -> member.isPresent()).orElseThrow(Exception::new);
    }

    @PutMapping("/{memberNo}/password")
    public void editPwd(@PathVariable Long memberNo, @Valid @RequestBody EditPwdDto pwdDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        memberService.editPwd(memberNo, pwdDto);
    }

    @PutMapping("/{memberNo}/nickname")
    public void editNickname(@PathVariable Long memberNo, @Valid @RequestBody EditNicknameDto nicknameDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("nickname", nicknameDto.getNickname());
        memberRepository.updateNickname(edit);
    }

    @PutMapping("/{memberNo}/phone")
    public void editPhone(@PathVariable Long memberNo, @Valid @RequestBody EditPhoneDto phoneDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("phone", phoneDto.getPhone());
        memberRepository.updatePhone(edit);
    }

    @PostMapping("/{memberNo}/address")
    public void addressList(@PathVariable String memberNo, @Valid @RequestBody AddressDto addressDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        memberService.addAddress(memberNo, addressDto);
    }

    @PutMapping("/{memberNo}/address/{addressNo}")
    public void editMainAddress(@PathVariable String memberNo, @PathVariable String addressNo) {
        memberRepository.updateMainAddress1(memberNo);
        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("addressNo", addressNo);
        memberRepository.updateMainAddress2(edit);
    }

    @DeleteMapping("/{memberNo}/address/{addressNo}")
    public void deleteAddress(@PathVariable String memberNo, @PathVariable String addressNo) {
        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("addressNo", addressNo);

        if (memberRepository.readAddress(edit).getIsMain().equals("Y")) {
            throw new IllegalArgumentException("메인 주소지는 삭제할 수 없습니다.");
        }

        memberRepository.deleteAddress(edit);
    }

}
