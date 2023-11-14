package com.api.shop_project.domain.member;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Address {

    private String City;

    private String street;

    private String zipcode;

}
