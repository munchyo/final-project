package com.goodday.proj.api.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    @NotNull
    Long memberNo;

    @NotNull
    Long proNo;

    @Min(value = 1)
    @NotNull
    Integer quantity;

}
