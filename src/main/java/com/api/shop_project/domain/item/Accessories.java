package com.api.shop_project.domain.item;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@DiscriminatorValue("accessories")
@NoArgsConstructor
@AllArgsConstructor
public class Accessories extends Item{

    @Enumerated(value = EnumType.STRING)
    private AccessoryType accessoryType;

}
