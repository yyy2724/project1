package com.api.shop_project.config;

import com.api.shop_project.domain.member.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private Long memberId;

    public UserPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_USER")
                ));
        this.memberId = member.getId();
    }

    public Long getMemberId() {
        return memberId;
    }
}
