package com.api.shop_project.config;

import com.api.shop_project.domain.member.Member;
import com.api.shop_project.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class ShopMockSecurityContext implements WithSecurityContextFactory<ShopMocUser> {

    private final MemberRepository memberRepository;

    @Override
    public SecurityContext createSecurityContext(ShopMocUser annotation) {
        Member member = Member.builder()
                .email(annotation.email())
                .name(annotation.name())
                .password(annotation.password())
                .role(annotation.role())
                .build();

            memberRepository.save(member);

            MyUserDetails userPrincipal = new MyUserDetails(member);

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_USER");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                member.getPassword(),
                List.of(role)
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
