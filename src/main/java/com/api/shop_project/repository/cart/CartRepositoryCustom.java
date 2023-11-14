package com.api.shop_project.repository.cart;

import com.api.shop_project.dto.response.cart.CartFindOne;
import com.api.shop_project.dto.response.cart.CartResponse;

import java.util.List;

public interface CartRepositoryCustom{

    List<CartResponse> cartFindOne(CartFindOne cartFindOne);
}
