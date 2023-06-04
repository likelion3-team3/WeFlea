package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {


    @Query("select sk from SearchKeyword sk")
    List<SearchKeyword> findAll();
}
