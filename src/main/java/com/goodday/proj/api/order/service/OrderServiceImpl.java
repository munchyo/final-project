package com.goodday.proj.api.order.service;

import com.goodday.proj.api.member.model.Address;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.order.dto.OrderDto;
import com.goodday.proj.api.order.dto.OrderForm;
import com.goodday.proj.api.order.repository.OrderRepository;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;

    @Override
    public Map<String, Object> orderView(OrderDto orderDto) {
        Map<String, Object> view = new HashMap<>();
        Product product = shopRepository.findByNo(orderDto.getProNo());

        // 재고가 0이면 결제 불가
        if (product.getProInventory() == 0) {
            throw new RuntimeException("재고가 없습니다.");
        }

        // 1. 회원 이름, 폰번
        Optional<Member> memberAndAddressByNo = memberRepository.findMemberAndAddressByNo(orderDto.getMemberNo());
        Member member = memberAndAddressByNo.filter(member1 -> memberAndAddressByNo.isPresent()).orElseThrow(RuntimeException::new);
        view.put("memberNo", member.getMemberNo());
        view.put("name", member.getName());
        view.put("phone", member.getPhone());

        // 2. 주소 갖고오기
        List<Address> addressList = memberRepository.findAddressListByMemberNo(orderDto.getMemberNo());
        view.put("addressList", addressList);

        // 3. product 정보 갖고오기
        view.put("product", product);

        // 4. 수량도 갖고와서 price 계산때려주기
        int orderPrice = product.getProPrice() * orderDto.getQuantity();
        view.put("orderPrice", orderPrice);

        return view;
    }

    @Override
    public int order(OrderForm form) {
        if (shopRepository.findByNo(form.getProNo()).getProInventory() == 0) {
            throw new RuntimeException("재고가 없습니다.");
        }

        Map<String, Object> addOrder = new HashMap<>();
        addOrder.put("orderNo", createOrderNo());
        addOrder.put("quantity", form.getQuantity());
        addOrder.put("price", form.getPrice() * form.getQuantity());
        addOrder.put("proNo", form.getProNo());
        addOrder.put("memberNo", form.getMemberNo());
        addOrder.put("orderMethod", form.getOrderMethod());
        addOrder.put("addressNo", form.getAddressNo());
        addOrder.put("orderRequest", form.getOrderRequest());

        return orderRepository.saveOrder(addOrder);
    }

    private long createOrderNo() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        String formattedDateTime = currentDateTime.format(formatter);

        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;

        return Long.parseLong(formattedDateTime + randomNumber);
    }
}
