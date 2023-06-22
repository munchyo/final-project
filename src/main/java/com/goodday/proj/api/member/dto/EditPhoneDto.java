package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditPhoneDto {

    @NotBlank
    @Size(max = 11)
    @Pattern(regexp = "[0-9]+", message = "전화번호 형식이 잘못되었습니다.")
    String phone;

}
