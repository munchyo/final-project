package com.goodday.proj.api.member.service;

import com.goodday.proj.api.member.dto.*;
import com.goodday.proj.api.member.model.Member;

public interface MemberService {

    MemberSessionInfo login(LoginFormDto login);

    int register(RegisterFormDto registerUser);

    int editPwd(Long memberNo, EditPwdDto pwdDto);

    void addAddress(String memberNo, AddressDto addressDto);
}
