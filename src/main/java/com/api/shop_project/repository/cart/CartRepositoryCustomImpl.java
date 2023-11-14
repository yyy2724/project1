package com.api.shop_project.repository.cart;

import com.api.shop_project.domain.cart.QCart;
import com.api.shop_project.domain.cart.QCartItem;
import com.api.shop_project.domain.item.QItem;
import com.api.shop_project.domain.member.QMember;
import com.api.shop_project.dto.response.cart.CartFindOne;
import com.api.shop_project.dto.response.cart.CartResponse;
import com.api.shop_project.exception.ValueException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.api.shop_project.domain.member.QMember.member;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom{

    private final JPAQueryFactory query;

    /**
     * QueryDSL -> 매핑된 엔티티에서 원하는 정보만 가져오기 위해 사용(성능 N+1 해결)
     *             (상품 ID, 상품 이름, 장바구니에 담을 상품 총 개수, 상품 가격, 장바구니에 담은 가격 총합)
     */
    @Override
    public List<CartResponse> cartFindOne(CartFindOne cartFindOne) {

        QCartItem cartItem = QCartItem.cartItem;
        QCart cart = QCart.cart;
        QMember member = QMember.member;
        QItem item = QItem.item;

        List<Tuple> queryResults = query
                .select(
                        item.id,
                        item.name,
                        cartItem.count.sum(),
                        item.price,
                        cartItem.totalPrice.sum())
                .from(cartItem)
                .join(cart).on(cartItem.cart.id.eq(cart.id))
                .join(item).on(cartItem.item.id.eq(item.id))
                .where(cart.member.id.eq(cartFindOne.getMemberId()))
                .groupBy(item.id)
                .fetch();

        List<CartResponse> cartResponses = new ArrayList<>();

        queryResults.forEach(tuple -> {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setItemId(tuple.get(item.id));
            cartResponse.setName(tuple.get(item.name));
            cartResponse.setCount(tuple.get(cartItem.count.sum()));
            cartResponse.setPrice(tuple.get(item.price));
            cartResponse.setTotalPrice(tuple.get(cartItem.totalPrice.sum()));
            cartResponses.add(cartResponse);
        });
        return cartResponses;
    }

    private BooleanExpression cartIdLike(Long memberId) {
        if (memberId == null) {
            throw new ValueException();
        }

        return member.id.ne(memberId);

    }

}
