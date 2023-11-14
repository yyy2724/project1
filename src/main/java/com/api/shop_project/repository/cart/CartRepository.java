package com.api.shop_project.repository.cart;

import com.api.shop_project.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
    List<Cart> findByMemberId(Long memberId);

}
