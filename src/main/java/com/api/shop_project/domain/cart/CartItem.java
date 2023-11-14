package com.api.shop_project.domain.cart;

import com.api.shop_project.domain.cart.Cart;
import com.api.shop_project.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int count;

    private int totalPrice;

    @Builder
    public CartItem(Long id, Cart cart, Item item, int count, int totalPrice) {
        this.id = id;
        this.cart = cart;
        this.item = item;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
