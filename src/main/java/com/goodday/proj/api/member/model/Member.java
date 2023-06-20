package com.goodday.proj.api.member.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {

    Long memberNo;
    String memberId;
    String pwd;
    String name;
    Date enrollDate;
    String nickname;
    String email;
    String phone;
    List<Address> address;
    String admin;

}
