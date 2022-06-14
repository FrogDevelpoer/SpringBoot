package com.shopping.service;

import com.shopping.dto.OrderDto;
import com.shopping.entity.Item;
import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderItem;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email){
        // 상품 ID를 이용하여 주문할 상품 정보를 조회
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        // 로그인 된 email정보를 사용하여 회원 Entity 조회
        Member member = memberRepository.findByEmail(email);

        // 주문할 상품들을 저장할 리스트 객체
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();

        // 상품 Entity와 주문 수량을 이용하여 주문 상품에 대한 Entity 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        // 주문 상품을 주문 상품 목록에 채워 넣음
        orderItemList.add(orderItem);

        // 회원 정보와 주문 상품 목록을 이용하여 주문 Entity 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);    // 주문 Entity 저장
        return order.getId();
    }
}
