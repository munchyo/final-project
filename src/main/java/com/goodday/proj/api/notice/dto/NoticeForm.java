package com.goodday.proj.api.notice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoticeForm {
    @NotNull
    Long memberNo;

    @NotEmpty
    String noticeTitle;

    @NotEmpty
    String noticeContent;

    List<MultipartFile> images;
}
