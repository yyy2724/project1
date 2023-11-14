package com.api.shop_project.dto.response.cart;

import com.api.shop_project.domain.cart.Cart;
import com.api.shop_project.domain.cart.CartItem;
import com.api.shop_project.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class CartList {

    private List<Cart> carts = new ArrayList<>();
}
