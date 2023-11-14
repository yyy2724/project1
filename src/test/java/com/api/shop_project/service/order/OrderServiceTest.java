package com.api.shop_project.service.order;

import com.api.shop_project.domain.item.Filters;
import com.api.shop_project.domain.item.Top;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import com.api.shop_project.domain.order.Order;
import com.api.shop_project.dto.response.order.OrderSearch;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.cart.CartRepository;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.order.OrderItemRepository;
import com.api.shop_project.repository.order.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @AfterEach
    void clean() {
        cartRepository.deleteAll();
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 요청")
    public void test1() {
        // given
        Member member = Member.builder()
                .email("user@gmail.com")
                .name("user")
                .password("1111")
                .phone("010-1111-1111")
                .role(Role.USER)
                .build();

        Member memberSave = memberRepository.save(member);

        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        int orderCount = 2;

        // when
        orderService.order(memberSave.getId(), itemSave.getId(), orderCount);

        // then
        Long id = orderRepository.findAll().get(0).getId();
        Order order = orderRepository.findById(id).get();

    }

//    @Test
//    @DisplayName("주문 초과")
//    public void test2() {
//        // given
//        Member member = Member.builder()
//                .email("user@gmail.com")
//                .name("user")
//                .password("1111")
//                .phone("010-1111-1111")
//                .role(Role.USER)
//                .build();
//
//        Member memberSave = memberRepository.save(member);
//
//        Top top = Top.builder()
//                .filters(Filters.MAN)
//                .name("옷1")
//                .price(10000)
//                .stockQuantity(10)
//                .top_Size("95")
//                .build();
//
//        Top itemSave = itemRepository.save(top);
//
//        int orderCount = 11;
//
//        // when
//        Long orderId = orderService.order(memberSave.getId(), itemSave.getId(), orderCount);
//
//        // then
//
//    }

    @Test
    @DisplayName("주문 취소")
    public void test3() {
        // given
        Member member = Member.builder()
                .email("user@gmail.com")
                .name("user")
                .password("1111")
                .phone("010-1111-1111")
                .role(Role.USER)
                .build();

        Member memberSave = memberRepository.save(member);

        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);


        int orderCount = 2;

        orderService.order(memberSave.getId(), itemSave.getId(), orderCount);
        // when
        Long id = orderRepository.findAll().get(0).getId();
        orderService.cancel(id);

        // then
    }

    @Test
    @DisplayName("주문 조회")
    public void test4 () {
        // given
        Member member = Member.builder()
                .email("user@gmail.com")
                .name("user")
                .password("1111")
                .phone("010-1111-1111")
                .role(Role.USER)
                .build();

        Member memberSave = memberRepository.save(member);

        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        OrderSearch orderSearch = new OrderSearch();

        orderSearch.setMemberName("user");


        // when
        List<Order> orders = orderService.findAll(orderSearch);

        // then
    }

}