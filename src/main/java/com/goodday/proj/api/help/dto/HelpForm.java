package com.goodday.proj.api.help.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpForm {
    @NotEmpty
    String helpType;

    @NotEmpty
    String helpTitle;

    @NotEmpty
    String helpContent;

    @NotNull
    Long memberNo;

    List<MultipartFile> images;
}
