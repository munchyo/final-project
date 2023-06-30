package com.goodday.proj.api.mypage.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoList {
    Long calNo;
    Date calDate;
    Integer period;
    String goal;
    Long memberNo;
    String nickname;
    Integer calStatus;

    public TodoList(Date calDate, Integer period, String goal, Long memberNo) {
        this.calDate = calDate;
        this.period = period;
        this.goal = goal;
        this.memberNo = memberNo;
    }
}
