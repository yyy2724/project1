package com.api.shop_project.repository.order;

import com.api.shop_project.domain.order.Order;
import com.api.shop_project.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByOrder(Order orderGet);
}
