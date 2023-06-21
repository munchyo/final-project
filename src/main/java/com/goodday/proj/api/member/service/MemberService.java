package com.goodday.proj.api.member.service;

import com.goodday.proj.api.member.dto.LoginFormDto;
import com.goodday.proj.api.member.dto.RegisterFormDto;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import com.goodday.proj.api.member.model.Member;

public interface MemberService {

    MemberSessionInfo login(LoginFormDto login);

    int register(RegisterFormDto registerUser);

}
