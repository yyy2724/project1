package com.api.shop_project.service.item;

import com.api.shop_project.domain.item.Item;
import com.api.shop_project.dto.response.ItemResponse;
import com.api.shop_project.repository.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponse getItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("상품 X"));

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .filters(item.getFilters())
                .build();

    }

    public List<ItemResponse> itemList() {
        List<ItemResponse> itemResponses;

        List<Item> all = itemRepository.findAll();

        itemResponses = all.stream().map(item -> ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .filters(item.getFilters())
                .build()).collect(Collectors.toList());

        return itemResponses;
    }

}
