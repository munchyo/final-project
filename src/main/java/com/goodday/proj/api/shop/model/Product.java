package com.goodday.proj.api.shop.model;

import com.goodday.proj.api.file.model.UploadFile;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

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
    UploadFile thumbnail;
    List<UploadFile> images;

    public Product(long proNo, String proName, String proContent, int proPrice, int proInventory) {
        this.proNo = proNo;
        this.proName = proName;
        this.proContent = proContent;
        this.proPrice = proPrice;
        this.proInventory = proInventory;
    }
}
