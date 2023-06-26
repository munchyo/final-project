package com.goodday.proj.api.calorie.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaloriePrescribeForm {
    @NotEmpty
    String gender;

    @NotNull
    Integer weight;

    @NotNull
    Integer height;

    @NotNull
    Integer age;

    @NotNull
    Integer activity;

    @NotEmpty
    String target;
}
