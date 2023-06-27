package com.goodday.proj.api.free.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreeBoard {
    Integer boNo;
    String boTitle;
    String boContent;
    Date boCreateDate;
    Integer memberNo;
    String boStatus;
}
