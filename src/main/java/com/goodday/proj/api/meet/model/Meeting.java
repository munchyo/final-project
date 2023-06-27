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
    int meetTotal;
    String openKakao;
    Date meetCreateDate;
    String memberNo;
    UploadFile thumbnail;
}
