package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Optional;

@Mapper
public interface ShopMapper extends ShopRepository {

    @Override
    int countProductList();

    @Override
    ArrayList<Product> selectProductList(RowBounds rowBounds);

    @Override
    int save(Product product);

    @Override
    Optional<Product> findByNo(Long proNo);
}
