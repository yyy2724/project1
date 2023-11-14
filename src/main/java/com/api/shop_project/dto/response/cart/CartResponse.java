package com.api.shop_project.dto.response.cart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartResponse {

    private Long itemId;

    private String name;

    private int count;

    private int price;

    private int totalPrice;

}
