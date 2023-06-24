package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Optional;

public interface ShopRepository {

    int countProductList();

    ArrayList<Product> selectProductList(RowBounds rowBounds);

    int save(Product product);

    Optional<Product> findByNo(Long proNo);
}
