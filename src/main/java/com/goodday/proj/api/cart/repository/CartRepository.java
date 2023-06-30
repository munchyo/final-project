package com.goodday.proj.api.cart.repository;

import com.goodday.proj.api.cart.model.Cart;

import java.util.List;
import java.util.Map;

public interface CartRepository {
    int saveCart(Map<String, Long> cartMap);

    int deleteCartByCartNo(Long cartNo);

    List<Cart> findCartByMemberNo(Long memberNo);
}
