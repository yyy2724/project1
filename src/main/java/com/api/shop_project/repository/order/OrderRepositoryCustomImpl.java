package com.api.shop_project.repository.order;

import com.api.shop_project.domain.member.QMember;
import com.api.shop_project.domain.order.Order;
import com.api.shop_project.domain.order.OrderStatus;
import com.api.shop_project.domain.order.QOrder;
import com.api.shop_project.dto.response.order.OrderFindOne;
import com.api.shop_project.dto.response.order.OrderSearch;
import com.api.shop_project.exception.ValueException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.api.shop_project.domain.member.QMember.member;
import static com.api.shop_project.domain.order.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Order> findOne(OrderFindOne orderFindOne) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query.select(order)
                .from(order)
                .join(order.member, member)
                .where(order.orderStatus.eq(orderFindOne.getOrderStatus()), order.member.id.eq(orderFindOne.getMemberId()))
                .limit(1000)
                .fetch();

    }

    private BooleanExpression idLike(Long memberId) {
        if (memberId == null) {
            throw new ValueException();
        }
        return member.id.ne(memberId);

    }

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;
        return query.select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if (StringUtils.hasText(memberName)) {
            return null;
        }

        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return order.orderStatus.eq(orderStatus);
    }

}
