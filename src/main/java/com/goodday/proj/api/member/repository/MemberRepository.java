package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.dto.MemberSessionInfo;

import java.util.Optional;

public interface MemberRepository {

    int save(Member member);

    Optional<MemberSessionInfo> findById(String id);

    String pwdFindByEmail(String email);

    Optional<MemberSessionInfo> findByEmail(String email);

    String findPwdById(String id);

    Optional<Member> findMemberByNo(Long memberNo);
}
