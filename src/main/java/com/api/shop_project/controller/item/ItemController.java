package com.api.shop_project.controller.item;

import com.api.shop_project.dto.response.ItemResponse;
import com.api.shop_project.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/itemList")
    public String itemList(Model model) {

        List<ItemResponse> itemResponses = itemService.itemList();

        model.addAttribute("itemList", itemResponses);

        return "/item/itemList";
    }

    @GetMapping("/item/itemDetails/{itemId}")
    public String getItem(Model model, @PathVariable("itemId") Long itemId) {
        ItemResponse item = itemService.getItem(itemId);

        model.addAttribute("item", item);

        return "/item/itemDetail";

    }

}
