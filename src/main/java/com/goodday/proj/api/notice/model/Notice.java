package com.goodday.proj.api.notice.model;

import com.goodday.proj.api.file.model.UploadFile;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notice {
    long noticeNo;
    String noticeTitle;
    String noticeContent;
    Date noticeCreateDate;
    long memberNo;
    String noticeStatus;
    List<UploadFile> images;

    public Notice(String noticeTitle, String noticeContent, long memberNo, List<UploadFile> images) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.memberNo = memberNo;
        this.images = images;
    }
}
