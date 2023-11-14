package com.api.shop_project.dto.response.order;

import com.api.shop_project.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    public String memberName;
    public OrderStatus orderStatus;

}
