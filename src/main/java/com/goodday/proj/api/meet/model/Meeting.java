package com.goodday.proj.api.meet.model;

import com.goodday.proj.api.file.model.UploadFile;
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
    long meetNo;
    String meetTitle;
    String meetContent;
    String meetAddress;
    int application;
    int meetTotal;
    String openKakao;
    Date meetCreateDate;
    long memberNo;
    String nickname;
    UploadFile thumbnail;

    public Meeting(String meetTitle, String meetContent, String meetAddress, int meetTotal, String openKakao, long memberNo, UploadFile thumbnail) {
        this.meetTitle = meetTitle;
        this.meetContent = meetContent;
        this.meetAddress = meetAddress;
        this.meetTotal = meetTotal;
        this.openKakao = openKakao;
        this.memberNo = memberNo;
        this.thumbnail = thumbnail;
    }
}
