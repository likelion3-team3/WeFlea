package com.ll.weflea.boundedContext.search.service;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.entity.SearchKeyword;
import com.ll.weflea.boundedContext.search.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @Autowired
    SearchKeywordRepository searchKeywordRepository;


    @Test
    @DisplayName("모든 인기 검색 키워드 조회")
    public void t01_findAllSearchKeyword() throws Exception {

        //given
        //NotProd에서 인기키워드 생성
        //자전거, 의자, 냉장고, 노트북, 모니터, 아이패드, 스타벅스, 책상

        //when
        List<SearchKeyword> searchKeywords = searchService.findAllSearchKeyword();


        //then
        assertThat(searchKeywords.size()).isEqualTo(8);
        assertThat(searchKeywords.get(0).getName()).isEqualTo("자전거");
        assertThat(searchKeywords.get(1).getName()).isEqualTo("의자");
    }

    private static final int DEFAULT_SIZE = 12;

    @Test
    @DisplayName("lastSearchId가 null, keyword도 null이면 id가 가장 큰 순부터 조회")
    public void t02_findSearchesById() throws Exception {

        //given
        //NotProd에서 1000개의 search 객체 생성
        Long lastSearchId = null;
        String keyword = "";
        Pageable pageable = PageRequest.of(0, DEFAULT_SIZE);


        //when
        List<Search> searchList = searchService.findSearchesById(lastSearchId, keyword, pageable);


        //then
        assertThat(searchList.size()).isEqualTo(12);
        assertThat(searchList.get(0).getId()).isEqualTo(1000);

    }

    @Test
    @DisplayName("lastSearchId만 null, id가 가장 큰 순부터 조회")
    public void t03_findSearchesById() throws Exception {

        //given
        //NotProd에서 1000개의 search 객체 생성
        Long lastSearchId = null;
        String keyword = "노트북";
        Pageable pageable = PageRequest.of(0, DEFAULT_SIZE);


        //when
        List<Search> searchList = searchService.findSearchesById(lastSearchId, keyword, pageable);


        //then
        assertThat(searchList.size()).isEqualTo(12);
        assertThat(searchList.get(0).getTitle()).contains("노트북");
        assertThat(searchList.get(0).getId()).isEqualTo(1000);

    }

    @Test
    @DisplayName("lastSearchId가 null이 아닌 경우, lastSearchId-1부터 내림차순 조회")
    public void t04_findSearchesById() throws Exception {

        //given
        //NotProd에서 1000개의 search 객체 생성
        Long lastSearchId = 900L;
        String keyword = "노트북";
        Pageable pageable = PageRequest.of(0, DEFAULT_SIZE);


        //when
        List<Search> searchList = searchService.findSearchesById(lastSearchId, keyword, pageable);


        //then
        assertThat(searchList.size()).isEqualTo(12);
        assertThat(searchList.get(0).getTitle()).contains("노트북");
        assertThat(searchList.get(0).getId()).isEqualTo(899L);

    }



}