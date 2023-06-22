package com.goodday.proj.api.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {

    @NotEmpty(message = "주소가 비어있습니다.")
    String addr1;

    @NotEmpty(message = "주소가 비어있습니다.")
    String addr2;

    String addr3;

    String addr4;

}
