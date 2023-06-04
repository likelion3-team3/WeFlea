package com.ll.weflea.boundedContext.search.service;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.ll.weflea.boundedContext.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    public List<Search> findByKeyword(String keyword) {
        List<Search> searchList = searchRepository.findByTitleContaining(keyword);

        return searchList;
    }

    public List<String> keywords() {
        List<String> keywords = new ArrayList<>();
        keywords.add("자전거");
        keywords.add("의자");
        keywords.add("아이폰");
        keywords.add("냉장고");
        keywords.add("노트북");
        keywords.add("패딩");
        keywords.add("아이패드");
        keywords.add("모니터");
        keywords.add("스타벅스");
        keywords.add("책상");

        return keywords;
    }
}
