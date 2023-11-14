package com.api.shop_project.controller.order;

import com.api.shop_project.config.ShopMocUser;
import com.api.shop_project.domain.item.Filters;
import com.api.shop_project.domain.item.Top;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.cart.CartRepository;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.order.OrderItemRepository;
import com.api.shop_project.repository.order.OrderRepository;
import com.api.shop_project.service.order.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.thymeleaf.check-template-location=false")
class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void clean() {
        orderRepository.deleteAll();
        itemRepository.deleteAll();
        orderItemRepository.deleteAll();
    }

    @Test
    @ShopMocUser
    @DisplayName("/order 요청시 주문을 한다.")
    public void test1() throws Exception {
        // given
        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        // expected
        mockMvc.perform(post("/order")
                        .param("itemId", String.valueOf(itemSave.getId()))
                        .param("count", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                ;

    }

    @Test
    @ShopMocUser
    @DisplayName("회원의 주문정보를 확인하고 싶다.")
    public void test2() throws Exception {
        // given

        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        orderService.order(1L, itemSave.getId(), 3);

        // expected
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 주문을 취소한다.")
    public void test3() throws Exception {
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

        orderService.order(memberSave.getId(), itemSave.getId(), 3);

        // expected
        mockMvc.perform(post("/order/{orderId}/cancel", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }

}