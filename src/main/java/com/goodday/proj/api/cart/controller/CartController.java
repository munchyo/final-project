package com.goodday.proj.api.cart.controller;

import com.goodday.proj.api.cart.model.Cart;
import com.goodday.proj.api.cart.repository.CartRepository;
import com.goodday.proj.api.cart.service.CartService;
import com.goodday.proj.api.constant.ErrorConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;

    /**
     * 내 장바구니 목록보기
     * @param memberNo
     * @return
     */
    @PostMapping
    public List<Cart> viewCart(@RequestParam Long memberNo) {
        return cartRepository.findCartByMemberNo(memberNo);
    }

    /**
     * 장바구니 추가
     * @param memberNo
     * @param proNo
     */
    @PostMapping
    public void addCart(@RequestParam Long memberNo, @RequestParam Long proNo) {
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("memberNo", memberNo);
        cartMap.put("proNo", proNo);

        int result = cartRepository.saveCart(cartMap);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 장바구니 삭제
     * @param cartNo
     */
    @DeleteMapping
    public void deleteCart(@RequestParam Long cartNo) {
        cartRepository.deleteCartByCartNo(cartNo);
    }
//    TODO 카트테스트
}
