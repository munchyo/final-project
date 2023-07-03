package com.goodday.proj.api.free.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreeBoardReply {
    long freeReNo;
    long memberNo;
    String nickname;
    String freeReContent;
    long freeNo;
    Date freeReCreateDate;
    Date freeReModifyDate;
    String freeReStatus;

    public FreeBoardReply(long memberNo, String freeReContent, long freeNo) {
        this.memberNo = memberNo;
        this.freeReContent = freeReContent;
        this.freeNo = freeNo;
    }

}
