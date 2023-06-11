package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Page<Goods> findAll(Pageable pageable);
}
