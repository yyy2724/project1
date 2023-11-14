package com.api.shop_project.dto.response.member;

import com.api.shop_project.domain.member.Address;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberSearch {

    private final String email;

    private final String name;

    private final String phone;

    private final Address address;

    @Builder
    public MemberSearch(String email, String name, String phone, Address address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
