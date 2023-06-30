package com.goodday.proj.api.cart.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CartMapper extends CartRepository {
    @Override
    int saveCart(Map<String, Long> cartMap);

    @Override
    int deleteCartByCartNo(Long cartNo);
}
