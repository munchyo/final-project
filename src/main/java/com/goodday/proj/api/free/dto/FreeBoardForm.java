package com.goodday.proj.api.free.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreeBoardForm {
    @NotBlank
    String freeTitle;

    @NotBlank
    String freeContent;

    @NotNull
    Long memberNo;

    MultipartFile file;
    List<MultipartFile> images;
}
