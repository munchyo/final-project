package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditNicknameDto {

    @NotBlank(message = "닉네임 공백이 있습니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10 글자 입력 해주세요.")
    String nickname;

}
