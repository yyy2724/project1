package com.api.shop_project.repository.order;

import com.api.shop_project.domain.order.Order;
import com.api.shop_project.dto.response.order.OrderFindOne;
import com.api.shop_project.dto.response.order.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findOne(OrderFindOne orderFindOne);

    List<Order> findAll(OrderSearch orderSearch);
}
