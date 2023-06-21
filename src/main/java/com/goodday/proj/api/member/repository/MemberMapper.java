package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper extends MemberRepository {

    @Override
    int save(Member member);

    @Override
    Optional<MemberSessionInfo> findById(String id);

    @Override
    Optional<MemberSessionInfo> findByEmail(String email);

    @Override
    String pwdFindByEmail(String email);

    @Override
    String findPwdById(String id);

    @Override
    Optional<Member> findMemberByNo(Long memberNo);

    @Override
    int updatePwd(Map edit);
}
