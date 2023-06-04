package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    List<Search> findByTitleContaining(String keyword);

}
