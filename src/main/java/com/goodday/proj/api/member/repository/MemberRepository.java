package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.dto.MemberSessionInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {

    int save(Member member);

    Optional<MemberSessionInfo> findSessionMemberById(String id);

    String pwdFindByEmail(String email);

    Optional<MemberSessionInfo> findSessionMemberByEmail(String email);

    String findPwdById(String id);

    Optional<MemberSessionInfo> findSessionMemberByNo(Long memberNo);

    Optional<Member> findMemberAndAddressByNo(Long memberNo);

    int updatePwd(Map edit);

    int updateNickname(Map edit);

    int updatePhone(Map edit);

    int saveAddress(Map saveAddress);

    int updateMainAddress2(Map edit);

    int updateMainAddress1(String memberNo);

    int deleteAddress(Map edit);

    Address readAddress(Map edit);

    int deleteMember(String memberNo);

    int saveKakaoMember(Map<String, Object> kakaoMemberInfo);

    List<Address> findAddressListByMemberNo(Long memberNo);
}
