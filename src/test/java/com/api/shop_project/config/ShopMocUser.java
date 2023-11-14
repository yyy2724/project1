package com.api.shop_project.config;

import com.api.shop_project.domain.member.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = ShopMockSecurityContext.class)
public @interface ShopMocUser {

    String name() default "ahnyunki";

    String email() default "ahnyunki@gmail.com";

    String password() default "1111";

    Role role() default Role.USER;
}
