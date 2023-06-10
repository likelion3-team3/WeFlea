package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
