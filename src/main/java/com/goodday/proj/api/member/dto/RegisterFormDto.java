package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterFormDto {

    @NotBlank
    @Size(min = 5, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    String id;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()]+")
    String pwd;

    @NotBlank
    @Size(min = 2, max = 10)
    String nickname;

    @NotBlank
    @Size(min = 2, max = 10)
    String name;

    @NotEmpty
    String addr1;

    @NotEmpty
    String addr2;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(max = 11)
    @Pattern(regexp = "[0-9]+")
    String phone;

}
