package com.goodday.proj.api.member.service;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.dto.EditPwdDto;
import com.goodday.proj.api.member.dto.LoginFormDto;
import com.goodday.proj.api.member.dto.RegisterFormDto;
import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
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
        log.debug("login [{}]", login);
        Optional<MemberSessionInfo> member = memberRepository.findById(login.getId());
        log.debug("session [{}]", member);
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
        if (!bcrypt.matches(pwdDto.getNewPwd(), memberRepository.findMemberByNo(memberNo).get().getPwd())) {
            throw new RuntimeException(ErrorConst.findError);
        }

        Map edit = new HashMap();
        edit.put("memberNo", memberNo);
        edit.put("pwd", bcrypt.encode(pwdDto.getNewPwd()));

        return memberRepository.updatePwd(edit);
    }

//    private void insertAddress(RegisterFormDto registerUser, Member member, boolean isMain) {
//        String newAddress = registerUser.getAddr1() + "/" + registerUser.getAddr2();
//
//        Address address = new Address();
//        address.setAddress(newAddress);
//        address.setIsMain("Y");
//        if (!isMain) {
//            address.setIsMain("N");
//        }
//
//        List<Address> addressList = new ArrayList<>();
//        addressList.add(address);
//        member.setAddress(addressList);
//
//    }


}
