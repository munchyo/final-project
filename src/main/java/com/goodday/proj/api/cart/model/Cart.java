package com.goodday.proj.api.cart.model;

import com.goodday.proj.api.file.model.UploadFile;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    long cartNo;
    long proNo;
    String proName;
    String proPrice;
    long memberNo;
    UploadFile thumbnail;
}
