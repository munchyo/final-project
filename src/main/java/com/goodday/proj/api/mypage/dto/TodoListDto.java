package com.goodday.proj.api.mypage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoListDto {

    @NotEmpty
    Date calDate;

    @NotNull
    Integer period;

    @NotEmpty
    String goal;

}
