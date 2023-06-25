package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Map;

public interface ShopRepository {

    int countProductList();

    ArrayList<Product> selectProductList(RowBounds rowBounds);

    int save(Product product);

    Product findByNo(Long proNo);

    int saveImages(Map images);

}
