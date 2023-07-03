package com.goodday.proj.api.free.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreeBoardReplyForm {
    @NotNull
    Long memberNo;

    @NotEmpty
    String freeReContent;
}
