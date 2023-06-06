package com.ll.weflea.boundedContext.search.service;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.entity.SearchKeyword;
import com.ll.weflea.boundedContext.search.repository.SearchKeywordRepository;
import com.ll.weflea.boundedContext.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;
    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional
    public void create(String area, String imageLink, String link, String price, String provider, String title) {
        Search search = Search.builder()
                .area(area)
                .imageLink(imageLink)
                .link(link)
                .price(price)
                .provider(provider)
                .title(title)
                .build();

        searchRepository.save(search);
    }

    public List<Search> findSearchesById(Long lastSearchId, String keyword, Pageable pageable) {

        return searchRepository.findSearchesById(lastSearchId, keyword, pageable);
    }


    public List<SearchKeyword> findAllSearchKeyword() {
        return searchKeywordRepository.findAll();
    }

    //NotProd에 데이터 넣기 위한임시 테스트 메서드
    @Transactional
    public void createSearchKeyword(String name) {
        SearchKeyword searchKeyword = SearchKeyword.create(name);
        searchKeywordRepository.save(searchKeyword);
    }


}
