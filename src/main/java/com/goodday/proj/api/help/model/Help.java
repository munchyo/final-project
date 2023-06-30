package com.goodday.proj.api.help.model;

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
public class Help {
    long helpNo;
    String helpType;
    String helpTitle;
    String helpContent;
    Date helpCreateDate;
    Long memberNo;
    String nickname;
    List<UploadFile> images;
    HelpReply reply;

    public Help(String helpType, String helpTitle, String helpContent, Long memberNo) {
        this.helpType = helpType;
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
        this.memberNo = memberNo;
    }

    public Help(String helpType, String helpTitle, String helpContent, Long memberNo, List<UploadFile> images) {
        this.helpType = helpType;
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
        this.memberNo = memberNo;
        this.images = images;
    }
}
