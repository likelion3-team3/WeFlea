package com.ll.weflea.boundedContext.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/search")
public class SearchController {

    @GetMapping("/list")
    public String searchList() {
        return "user/search/list";
    }


}
