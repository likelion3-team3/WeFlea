package com.ll.weflea.boundedContext.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/weflea")
public class GoodsController {

    @GetMapping("/list")
    public String wefleaList() {
        return "/user/weflea/list";
    }


}
