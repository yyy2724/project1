package com.api.shop_project.controller.cart;

import com.api.shop_project.config.ShopMocUser;
import com.api.shop_project.domain.item.Filters;
import com.api.shop_project.domain.item.Top;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.cart.CartRepository;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.order.OrderItemRepository;
import com.api.shop_project.repository.order.OrderRepository;
import com.api.shop_project.service.cart.CartService;
import com.api.shop_project.service.order.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(properties = "spring.thymeleaf.check-template-location=false")
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @AfterEach
    void clean() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }


    @Test
    @ShopMocUser
    @DisplayName("장바구니에 상품 담기")
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
        mockMvc.perform(post("/cart/add")
                        .param("itemId", String.valueOf(itemSave.getId()))
                        .param("count", "3")
                        .param("total", String.valueOf(itemSave.getPrice() * itemSave.getStockQuantity())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @ShopMocUser
    @DisplayName("상품을 장바구니에 담지 않았을 때 회원이 장바구니를 확인한다.")
    public void test2() throws Exception {
        mockMvc.perform(get("/carts"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @ShopMocUser
    @DisplayName("상품을 장바구니에 담은 상태에서 회원이 장바구니를 확인한다.")
    public void test3() throws Exception {
        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        int count = 5;

        cartService.addCart(1L, itemSave.getId(), count, itemSave.getPrice() * count);

        mockMvc.perform(get("/carts"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @ShopMocUser
    @DisplayName("회원이 장바구니에 있는 상품을 삭제한다.")
    public void test4() throws Exception {
        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Top itemSave = itemRepository.save(top);

        int count = 5;

        cartService.addCart(1L, itemSave.getId(), count, itemSave.getPrice() * count);

        mockMvc.perform(post("/carts/{itemId}", itemSave.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

}