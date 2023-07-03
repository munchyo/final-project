package com.goodday.proj.api.free.model;

import com.goodday.proj.api.file.model.UploadFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreeBoard {
    long freeNo;
    String freeTitle;
    String freeContent;
    Date freeCreateDate;
    Date freeModifyDate;
    long memberNo;
    String nickname;
    String freeStatus;
    UploadFile file;
    List<UploadFile> images;
    List<FreeBoardReply> replies;

    public FreeBoard(long freeNo, String freeTitle, String freeContent) {
        this.freeNo = freeNo;
        this.freeTitle = freeTitle;
        this.freeContent = freeContent;
    }
}
