package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper extends MemberRepository {

    @Override
    int createMember(Member member);

    @Override
    Optional<Member> findById(String id);

    @Override
    Optional<Member> findByEmail(String email);

    @Override
    String pwdFindByEmail(String email);

    @Override
    int createAddress(List<Address> address, Long memberNo);
}
