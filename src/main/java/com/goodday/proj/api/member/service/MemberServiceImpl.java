package com.goodday.proj.api.member.service;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.dto.*;
import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bcrypt;

    @Override
    public MemberSessionInfo login(LoginFormDto login) {
        Optional<MemberSessionInfo> member = memberRepository.findSessionMemberById(login.getId());
        if(member.isEmpty()){
            throw new RuntimeException(ErrorConst.loginError);
        }

        if (!bcrypt.matches(login.getPwd(), memberRepository.findPwdById(login.getId()))) {
            throw new RuntimeException(ErrorConst.loginError);
        }

        return member.get();
    }

    @Override
    public int register(RegisterFormDto registerUser) {
        if (memberRepository.findSessionMemberById(registerUser.getId()).isPresent()) {
            throw new IllegalArgumentException("아이디가 중복입니다.");
        } else if (memberRepository.findSessionMemberByEmail(registerUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일이 중복입니다.");
        }

        Member member = new Member();

        if (!(registerUser.getAddr1()+registerUser.getAddr2()).isEmpty()) {
            String newAddress = registerUser.getAddr1() + "/" + registerUser.getAddr2() + "/" + registerUser.getAddr3() + "/" + registerUser.getAddr4();

            Address address = new Address();
            address.setAddress(newAddress);
            address.setIsMain("Y");

            List<Address> addressList = new ArrayList<>();
            addressList.add(address);
            member.setAddress(addressList);
        }
        member.setMemberId(registerUser.getId());
        member.setPwd(bcrypt.encode(registerUser.getPwd()));
        member.setName(registerUser.getName());
        member.setNickname(registerUser.getNickname());
        member.setEmail(registerUser.getEmail());
        member.setPhone(registerUser.getPhone());

        return memberRepository.save(member);
    }

    @Override
    public int editPwd(Long memberNo, EditPwdDto pwdDto) {
        if (!bcrypt.matches(pwdDto.getCurrentPwd(), memberRepository.findMemberAndAddressByNo(memberNo).get().getPwd())) {
            throw new RuntimeException(ErrorConst.findError);
        }

        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("pwd", bcrypt.encode(pwdDto.getNewPwd()));

        return memberRepository.updatePwd(edit);
    }

    @Override
    public void addAddress(String memberNo, AddressDto addressDto) {
        String address = addressDto.getAddr1() + "/" + addressDto.getAddr2() + "/" + addressDto.getAddr3() + "/" + addressDto.getAddr4();
        Map saveAddress = new HashMap();
        saveAddress.put("memberNo", memberNo);
        saveAddress.put("address", address);
        memberRepository.saveAddress(saveAddress);
    }

}
