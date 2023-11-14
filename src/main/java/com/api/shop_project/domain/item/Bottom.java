package com.api.shop_project.domain.item;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@SuperBuilder
@DiscriminatorValue("bottom")
@NoArgsConstructor
@AllArgsConstructor
public class Bottom extends Item{

    private String bottom_Size;

}
