package com.goodday.proj.api.member.service;

import com.goodday.proj.api.member.dto.*;

import java.util.Map;
import java.util.Optional;

public interface MemberService {

    MemberSessionInfo login(LoginFormDto login);

    int register(RegisterFormDto registerUser);

    int editPwd(Long memberNo, EditPwdDto pwdDto);

    void addAddress(String memberNo, AddressDto addressDto);

    int naverRegister(Optional<MemberSessionInfo> optionalMember, Map<String, String> naverMemberInfo);
}
