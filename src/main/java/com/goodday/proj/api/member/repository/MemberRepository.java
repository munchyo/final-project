package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    int createMember(Member member);

    Optional<Member> findById(String id);

    String pwdFindByEmail(String email);

    Optional<Member> findByEmail(String email);

    int createAddress(List<Address> address, Long memberNo);
}
