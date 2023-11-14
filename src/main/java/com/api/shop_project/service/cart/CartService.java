package com.api.shop_project.service.cart;

import com.api.shop_project.config.MyUserDetails;
import com.api.shop_project.domain.cart.Cart;
import com.api.shop_project.domain.cart.CartItem;
import com.api.shop_project.domain.item.Item;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.dto.response.cart.CartFindOne;
import com.api.shop_project.dto.response.cart.CartResponse;
import com.api.shop_project.exception.ValueException;
import com.api.shop_project.repository.Item.ItemRepository;
import com.api.shop_project.repository.cart.CartItemRepository;
import com.api.shop_project.repository.cart.CartRepository;
import com.api.shop_project.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    /**
     * 회원 정보와 상품 정보를 받고 그 정보를 바탕으로 장바구니를 생성한다.
     */
    @Transactional
    public void addCart(Long memberId, Long itemId, int count, int price) {
                                                                        // Custom 예외처리 생성 후 처리
        Member member = memberRepository.findById(memberId).orElseThrow(ValueException::new);

        Item item = itemRepository.findById(itemId).orElseThrow(ValueException::new);

        Cart cart = cartRepository.save(Cart.builder()
                .member(member)
                .build());

        CartItem cartItem = cartItemRepository.save(CartItem.builder()
                .item(item)
                .count(count)
                .totalPrice(price)
                .cart(cart)
                .build());
    }

    /**
     * QueryDSL을 사용해 장바구니에 저장된 상품 정보들을 가져온다.
     */
    public List<CartResponse> cartFindOne(Long memberId) {

        CartFindOne cartFindOne = new CartFindOne();

        cartFindOne.setMemberId(memberId);

        return cartRepository.cartFindOne(cartFindOne);



    }

    /**
     * 상품 ID를 조회 후 일치하는 상품 장바구니에서 제거
     */
    @Transactional
    public void cartCancel(Long itemId) {

        cartItemRepository.deleteByItemId(itemId);

    }
}
