package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindPwdForm {

    String email;

    @NotBlank(message = "비밀번호 공백이 있습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상 입력 해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()]+", message = "비밀번호는 영어 대소문자, 숫자, 특수문자만 가능합니다.")
    String pwd;

}
