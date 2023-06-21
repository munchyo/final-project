package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditNicknameDto {

    @NotBlank
    @Size(min = 2, max = 10)
    String nickname;

}
