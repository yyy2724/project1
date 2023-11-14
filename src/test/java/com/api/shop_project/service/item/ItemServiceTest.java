package com.api.shop_project.service.item;

import com.api.shop_project.domain.item.Filters;
import com.api.shop_project.domain.item.Item;
import com.api.shop_project.domain.item.Top;
import com.api.shop_project.repository.Item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    /**
     *  Top top = Top.builder()
     *                 .filters(Filters.MAN)
     *                 .name("옷1")
     *                 .price(10000)
     *                 .stockQuantity(10)
     *                 .top_Size("95")
     *                 .build();
     */
    @Test
    @DisplayName("아이템 여러개 생성")
    public void test1() {
        // given
        List<Top> tops = IntStream.range(0, 20)
                .mapToObj(i -> Top.builder()
                        .filters(Filters.MAN)
                        .name("옷 "+i)
                        .price(10000+i)
                        .stockQuantity(1000)
                        .top_Size("95")
                        .build()).collect(Collectors.toList());

        itemRepository.saveAll(tops);
        // when

        // then
    }

}