package com.api.shop_project.controller;

import com.api.shop_project.dto.response.ItemResponse;
import com.api.shop_project.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping({"","/index"})
    public String index(Model model){

        List<ItemResponse> itemResponses = itemService.itemList();

        model.addAttribute("item", itemResponses);

        return "index";
    }

    @GetMapping("/memberList")
    public String memberList(){
        return "/member/memberList";
    }

    @GetMapping("/insert")
    public String insert(){
        return "/item/insert";
    }


}
