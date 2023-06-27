package com.goodday.proj.api.meet.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingWriteForm {
    Long memberNo;
    String meetTitle;
    String meetContent;
    String addr1;
    String addr2;
    String addr3;
    String addr4;
    Integer meetTotal;
    String openKakao;
    MultipartFile thumbnail;
}
