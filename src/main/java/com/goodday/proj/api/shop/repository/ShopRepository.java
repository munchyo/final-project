package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface ShopRepository {

    int countProductList();

    ArrayList<Product> selectProductList(RowBounds rowBounds);

    int save(Product product);
}
