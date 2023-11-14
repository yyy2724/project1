package com.api.shop_project.controller.cart;

import com.api.shop_project.config.MyUserDetails;
import com.api.shop_project.config.UserPrincipal;
import com.api.shop_project.domain.cart.Cart;
import com.api.shop_project.domain.cart.CartItem;
import com.api.shop_project.dto.response.cart.CartResponse;
import com.api.shop_project.service.cart.CartService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public String addCart(@AuthenticationPrincipal MyUserDetails principal,
                        @RequestParam Long itemId,
                        @RequestParam int count,
                        @RequestParam int total) {

        cartService.addCart(principal.getMember().getId(), itemId, count, total);

        return "/index";

    }

    @GetMapping("/carts")
    public String cartList(@AuthenticationPrincipal MyUserDetails principal, Model model) {

        List<CartResponse> carts = cartService.cartFindOne(principal.getMember().getId());

        model.addAttribute("carts", carts);

        return "/cart/cart";
    }

    @PostMapping("/carts/{itemId}")
    public String cartCancel(@AuthenticationPrincipal MyUserDetails myUserDetails, @PathVariable("itemId") Long itemId) {

        cartService.cartCancel(itemId);

        return "/cart/cart";

    }
}
