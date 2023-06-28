package com.goodday.proj.api.meet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingWriteForm {

    @NotNull
    Long memberNo;

    @NotEmpty
    String meetTitle;

    @NotEmpty
    String meetContent;

    @NotEmpty
    String meetAddress;

    @NotNull
    @Min(value = 2)
    Integer meetTotal;

    String openKakao;

    MultipartFile thumbnail;
}
