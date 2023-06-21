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
    @Pattern(regexp = "[0-9]+")
    String phone;

}
