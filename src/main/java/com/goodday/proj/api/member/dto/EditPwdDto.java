package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditPwdDto {

    @NotBlank
    String currentPwd;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()]+")
    String newPwd;

}
