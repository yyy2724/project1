package com.api.shop_project.domain.order;

import com.api.shop_project.domain.item.Item;
import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int count;

    @Builder
    public OrderItem(Long id, Item item,Order order , int price, int count) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.price = price;
        this.count = count;
    }

    // service에서 넘겨 받은 데이터를 토대로 OrderItem을 만들고, item 재고 수량을 줄였다.
    public static OrderItem createOrderItem(Item item, int price, int count, Order order) {

        item.removeStock(count);

        return OrderItem.builder()
                .item(item)
                .price(price)
                .count(count)
                .order(order)
                .build();
    }

    public void cancel(int count) {

        item.addStock(count);
    }
}
