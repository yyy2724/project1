package com.api.shop_project.service.cart;

import com.api.shop_project.domain.cart.Cart;
import com.api.shop_project.domain.item.Bottom;
import com.api.shop_project.domain.item.Filters;
import com.api.shop_project.domain.item.Top;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import com.api.shop_project.dto.response.cart.CartList;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class CartServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartService cartService;



    @Test
    @DisplayName("물건 담기")
    public void test1() {
        // given
        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Long itemId = itemRepository.save(top).getId();

        Bottom bottom = Bottom.builder()
                .filters(Filters.WOMEN)
                .name("옷2")
                .price(10000)
                .stockQuantity(10)
                .bottom_Size("100")
                .build();

        Long itemId2 = itemRepository.save(bottom).getId();

        Member member = Member.builder()
                .email("user@gmail.com")
                .name("user")
                .password("1111")
                .phone("010-1111-1111")
                .role(Role.USER)
                .build();

        Long memberId = memberRepository.save(member).getId();


        // when

        int total = top.getPrice() * 10;

        cartService.addCart(memberId, itemId, top.getStockQuantity(), total);

        cartService.addCart(memberId, itemId2, top.getStockQuantity(), total);

        // then
    }

    @Test
    @DisplayName("장바구니 아이템 제거")
    public void test2 () {
        // given
        Top top = Top.builder()
                .filters(Filters.MAN)
                .name("옷1")
                .price(10000)
                .stockQuantity(10)
                .top_Size("95")
                .build();

        Long itemId = itemRepository.save(top).getId();

        Member member = Member.builder()
                .email("user@gmail.com")
                .name("user")
                .password("1111")
                .phone("010-1111-1111")
                .role(Role.USER)
                .build();

        Long memberId = memberRepository.save(member).getId();
        // when
        cartService.addCart(memberId, itemId, top.getStockQuantity(), 10000);

        cartService.cartCancel(itemId);

        // then
    }



}