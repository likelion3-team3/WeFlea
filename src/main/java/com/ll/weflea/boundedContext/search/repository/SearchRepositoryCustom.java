package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.dto.SearchDto;
import com.ll.weflea.boundedContext.search.entity.Search;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepositoryCustom {

    List<Search> findSearchesBySellDate(SearchDto searchDto, Pageable pageable);

    List<Search> findSearchesByPrice(SearchDto searchDto, Pageable pageable);
}
