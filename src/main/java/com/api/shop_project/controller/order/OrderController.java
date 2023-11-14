package com.api.shop_project.controller.order;

import com.api.shop_project.config.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import com.api.shop_project.domain.order.Order;
import com.api.shop_project.dto.response.order.OrderSearch;
import com.api.shop_project.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 회원 주문 상태 보여주기
     */
    @GetMapping("/order")
    public String getOrder(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        List<Order> order = orderService.findByOne(myUserDetails.getMember().getId());

        model.addAttribute("order", order);

        return "mypage/orderList";

    }

    /**
     * 상품 주문
     */
    @PostMapping("/order")
    public String order(@AuthenticationPrincipal MyUserDetails myUserDetails,
                        @RequestParam Long itemId,
                        @RequestParam int count) {

        orderService.order(myUserDetails.getMember().getId(), itemId, count);

        return "index";
    }

    /**
     * 관리자가 회원 주문 상품 전체 조회
     */
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findAll(orderSearch);
        model.addAttribute("orders", orders);

        return "index";
    }

    /**
     * 회원이 상품을 취소
     */
    @PostMapping("/order/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);

        return "index";
    }


}
