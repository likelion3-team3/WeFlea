package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/search")
@Slf4j
public class SearchController {

    private final SearchService searchService;
    private static final int DEFAULT_SIZE = 12;


    //전체조회
    @GetMapping("/all")
    public String searchAll(Model model, Integer size) {

        if (size == null) {
            size = DEFAULT_SIZE;
        }

        List<Search> searchList = searchService.findSearchesById(null, PageRequest.of(0, size));

        List<String> keywords = searchService.keywords();

        model.addAttribute("keywords", keywords);
        model.addAttribute("searchList", searchList);
        return "/user/search/list";
    }

    @GetMapping("/all/{lastSearchId}")
    @ResponseBody
    public Map<String, Object> searchByLastSearchId(Model model, @PathVariable String lastSearchId) {

        List<Search> searchList = searchService.findSearchesById(Long.parseLong(lastSearchId), PageRequest.of(0, DEFAULT_SIZE));

        List<String> keywords = searchService.keywords();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("keywords", keywords);
        map.put("searchList", searchList);
        return map;
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
