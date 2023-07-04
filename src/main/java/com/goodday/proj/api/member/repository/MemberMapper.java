package com.goodday.proj.api.member.repository;

import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper extends MemberRepository {

    @Override
    int save(Member member);

    @Override
    Optional<MemberSessionInfo> findSessionMemberById(String id);

    @Override
    Optional<MemberSessionInfo> findSessionMemberByEmail(String email);

    @Override
    String pwdFindByEmail(String email);

    @Override
    String findPwdById(String id);

    @Override
    Optional<MemberSessionInfo> findSessionMemberByNo(Long memberNo);

    @Override
    Optional<Member> findMemberAndAddressByNo(Long memberNo);

    @Override
    int updatePwd(Map edit);

    @Override
    int updateNickname(Map edit);

    @Override
    int updatePhone(Map edit);

    @Override
    int saveAddress(Map saveAddress);

    @Override
    int updateMainAddress1(String memberNo);

    @Override
    int updateMainAddress2(Map edit);

    @Override
    int deleteAddress(Map edit);

    @Override
    Address readAddress(Map edit);

    @Override
    int deleteMember(Long memberNo);

    @Override
    int saveKakaoMember(Map<String, Object> kakaoMemberInfo);

    @Override
    List<Address> findAddressListByMemberNo(Long memberNo);
}
