package com.goodday.proj.api.order.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.order.dto.OrderDto;
import com.goodday.proj.api.order.dto.OrderForm;
import com.goodday.proj.api.order.repository.OrderRepository;
import com.goodday.proj.api.order.service.OrderService;
import com.goodday.proj.api.shop.repository.ShopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    /**
     * 주문창 이동
     * @param orderDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/form")
    public Map<String, Object> orderView(@Valid @ModelAttribute OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        return orderService.orderView(orderDto);
    }

    /**
     * 주문
     * @param form
     * @param bindingResult
     */
    @PostMapping
    public void order(@Valid @ModelAttribute OrderForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        int result = orderService.order(form);
        if (result != 0) {
            log.info("{}({})님이 상품명 : [{}] 를 주문 하셨습니다.", memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getNickname(),
                    memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getMemberId(),
                    shopRepository.findByNo(form.getProNo()).getProName());
            //메일
        } else {
            log.info("{}({})님, 주문실패", memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getNickname(),
                    memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getMemberId());
            throw new RuntimeException(ErrorConst.orderError);
        }
    }
}
