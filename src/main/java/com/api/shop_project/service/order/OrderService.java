package com.api.shop_project.service.order;

import com.api.shop_project.domain.item.Item;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.order.Order;
import com.api.shop_project.domain.order.OrderItem;
import com.api.shop_project.domain.order.OrderStatus;
import com.api.shop_project.dto.response.order.OrderFindOne;
import com.api.shop_project.dto.response.order.OrderSearch;
import com.api.shop_project.exception.ValueException;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.order.OrderItemRepository;
import com.api.shop_project.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * 주문 로직
     */
    @Transactional
    public void order(Long memberId, Long itemId, int count) {

        // 회원 id로 회원 정보 가져왔다.
        Member member = memberRepository.findById(memberId).orElseThrow(ValueException::new);

        // 상품 id로 상품 정보를 가져왔다.
        Item item = itemRepository.findById(itemId).orElseThrow(ValueException::new);

        // 주문생성
        Order order = orderRepository.save(Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build());

        // 생성된 주문을 바탕으로 주문 시작
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count, order);

        orderItemRepository.save(orderItem);

    }

    /**
     * 주문 취소 로직
     */
    @Transactional
    public void cancel(Long orderId) {

        Order orderGet = orderRepository.findById(orderId).orElseThrow(ValueException::new);

        OrderItem orderItem = orderItemRepository.findByOrder(orderGet);

        orderItem.cancel(orderItem.getCount());

        orderItemRepository.delete(orderItem);

        Order order = Order.builder()
                .id(orderGet.getId())
                .member(orderGet.getMember())
                .orderStatus(OrderStatus.CANCEL)
                .build();

        orderRepository.save(order);
    }

    /**
     * 주문 조회
     */
    public List<Order> findAll(OrderSearch orderSearch) {

        return orderRepository.findAll(orderSearch);

    }


    /**
     * 주문 조회
     */
    public List<Order> findByOne(Long memberId) {

        OrderFindOne orderFindOne = new OrderFindOne();

        orderFindOne.setMemberId(memberId);
        orderFindOne.setOrderStatus(OrderStatus.ORDER);

        return orderRepository.findOne(orderFindOne);

    }
}
