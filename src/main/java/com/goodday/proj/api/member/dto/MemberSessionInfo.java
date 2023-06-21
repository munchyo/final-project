package com.goodday.proj.api.member.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberSessionInfo {

    Long memberNo;
    String memberId;
    String admin;
}
