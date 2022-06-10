package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY) // 상품들은 여러 개의 주문상품에 포함될 수 있습니다.
    @JoinColumn(name = "item_id")
    private Item item ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order ;

    private int orderPrice ; // 단가
    private int count ; // 수량
//    private LocalDateTime regTime ;
//    private LocalDateTime updateTime ;
}
