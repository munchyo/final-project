package com.goodday.proj.api.shop.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    long proNo;
    String proName;
    String proContent;
    int proPrice;
    int proInventory;
    Date proCreateDate;
    String thumbnail;

}
