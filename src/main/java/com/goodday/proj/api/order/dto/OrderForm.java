package com.goodday.proj.api.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderForm {

    @Min(value = 1)
    @NotNull
    Integer quantity;

    @NotNull
    Integer price;

    @NotNull
    Long proNo;

    @NotNull
    Long memberNo;

    @NotEmpty
    String orderMethod;

    @NotNull
    Long addressNo;

    @NotEmpty
    String orderRequest;

}
