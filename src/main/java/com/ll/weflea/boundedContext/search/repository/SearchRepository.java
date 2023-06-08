package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryCustom {

    List<Search> findByTitleContaining(String keyword);

    @Query("SELECT COUNT(DISTINCT s.id) FROM Search s")
    long countDistinctById();



}
