package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.entity.SearchKeyword;
import com.ll.weflea.boundedContext.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
    public String searchAll(Model model, String keyword) {


        List<Search> searchList = searchService.findSearchesById(null, keyword, PageRequest.of(0, DEFAULT_SIZE));

        List<SearchKeyword> keywords = searchService.findAllSearchKeyword();

        model.addAttribute("keywords", keywords);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchList", searchList);
        return "user/search/list";
    }

    @GetMapping("/all/{lastSearchId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchByLastSearchId(@PathVariable Long lastSearchId, String keyword) {

        List<Search> searchList = searchService.findSearchesById(lastSearchId, keyword, PageRequest.of(0, DEFAULT_SIZE));

        List<SearchKeyword> keywords = searchService.findAllSearchKeyword();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("keywords", keywords);
        map.put("searchList", searchList);
        return ResponseEntity.ok(map);
    }

}
