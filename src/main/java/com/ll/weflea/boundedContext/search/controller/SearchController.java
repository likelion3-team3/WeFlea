package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.search.dto.SearchDto;
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
    public String searchAll(Model model,
                            @RequestParam(defaultValue = "") String keyword,
                            @RequestParam(defaultValue = "") String provider,
                            @RequestParam(defaultValue = "1") Integer sortCode) {

        SearchDto searchDto = new SearchDto(keyword, provider, sortCode);

        List<Search> searchList = searchService.findSearchesById(searchDto, PageRequest.of(0, DEFAULT_SIZE));
        log.info("페이지 번호 = {}" , 0);

        List<SearchKeyword> keywords = searchService.findAllSearchKeyword();

        model.addAttribute("keywords", keywords);
        model.addAttribute("keyword", searchDto.getKeyword());
        model.addAttribute("searchList", searchList);
        return "user/search/list";
    }

    @GetMapping("/all/{pageNumber}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchByPageNumber(@PathVariable int pageNumber,
                                                                  @RequestParam(defaultValue = "") String keyword,
                                                                  @RequestParam(defaultValue = "") String provider,
                                                                  @RequestParam(defaultValue = "1") Integer sortCode) {

        log.info("페이지 번호 = {}" , pageNumber);

        SearchDto searchDto = new SearchDto(keyword, provider, sortCode);

        List<Search> searchList = searchService.findSearchesById(searchDto, PageRequest.of(pageNumber, DEFAULT_SIZE));

        Map<String, Object> map = new HashMap<>();

        map.put("searchList", searchList);
        return ResponseEntity.ok(map);
    }

}
