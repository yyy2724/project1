package com.api.shop_project.dto.response.order;

import com.api.shop_project.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderResponse {

    private String memberName;

    private String itemName;

    private int price;

    private int count;

    private OrderStatus orderStatus;

    private LocalDateTime createTime;

}
