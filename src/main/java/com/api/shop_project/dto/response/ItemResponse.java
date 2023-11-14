package com.api.shop_project.dto.response;

import com.api.shop_project.domain.item.Filters;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {

    public Long id;

    private String name;

    private int price;

    private Filters filters;

    @Builder
    public ItemResponse(Long id,String name, int price, Filters filters) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.filters = filters;
    }
}
