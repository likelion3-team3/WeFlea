package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
