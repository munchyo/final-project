package com.goodday.proj.api.order.model;

import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.member.model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    long orderNo;
    int quantity;
    int price;
    Date orderDate;
    long proNo;
    String proName;
    long memberNo;
    String name;
    String phone;
    String orderMethod;
    Address address;
    String orderRequest;
    UploadFile thumbnail;
}
