package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.naming.SpringContextVariableNames;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    // 위플리 장터 상품 등록 기능
    public RsData<Goods> create(String title, String area, String status, int price, String description) {
        Goods goods = Goods
                .builder()
                .title(title)
                .area(area)
                .status(status)
                .price(price)
                .description(description)
                .build();

        goodsRepository.save(goods);

        return RsData.of("S-1", "입력하신 상품이 등록되었습니다.");
    }
}
