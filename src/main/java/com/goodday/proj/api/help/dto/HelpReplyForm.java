package com.goodday.proj.api.help.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpReplyForm {

    @NotNull
    Long helpNo;

    @NotEmpty
    String helpReContent;

    @NotNull
    Long memberNo;
}
