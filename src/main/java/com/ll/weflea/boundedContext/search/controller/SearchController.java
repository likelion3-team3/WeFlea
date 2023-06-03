package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/search")
@Slf4j
public class SearchController {

    private final SearchService searchService;
    private static final int DEFAULT_SIZE = 10;


    //전체조회
    @GetMapping("/all")
    public String searchAll(Model model, Long lastSearchId, Integer size) {

        if (size == null) {
            size = DEFAULT_SIZE;
        }

        List<Search> searchList = searchService.findSearchesById(lastSearchId, PageRequest.of(0, size));

        List<String> keywords = searchService.keywords();

        model.addAttribute("keywords", keywords);
        model.addAttribute("searchList", searchList);
        return "user/search/tmpList";
    }

    //위플리에서 뽑은 인기 검색 키워드
    @GetMapping
    public String searchByKeyword(@RequestParam String keyword, Model model) {

        List<Search> searchList = searchService.findByKeyword(keyword);
        List<String> keywords = searchService.keywords();

        model.addAttribute("keyword", keyword);
        model.addAttribute("searches", searchList);
        model.addAttribute("keywords", keywords);

        return "user/search/keywordList";
    }



}
