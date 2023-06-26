package com.goodday.proj.api.meet.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Meeting {
    Long meetNo;
    String meetTitle;
    String meetContent;
    String meetAddress;
    String meetTotal;
    String openKakao;
    Date meetCreateDate;
    String memberNo;
    String meetStatus;
}
