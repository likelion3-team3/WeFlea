package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/search")
public class SearchController {

    private final SearchService searchService;


    @GetMapping("/list")
    public String searchAll(Model model) {
        List<String> keywords = searchService.keywords();

        model.addAttribute("keywords", keywords);
        return "user/search/list";
    }

    @GetMapping("/list/{keyword}")
    public String searchByKeyword(@PathVariable String keyword, Model model) {

        List<Search> searchList = searchService.findByKeyword(keyword);
        List<String> keywords = searchService.keywords();

        model.addAttribute("keyword", keyword);
        model.addAttribute("searches", searchList);
        model.addAttribute("keywords", keywords);

        return "user/search/keywordList";
    }


}
