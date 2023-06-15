package com.ll.weflea.base.initData;

import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Status;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.search.service.SearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(MemberService memberService, SearchService searchService, GoodsService goodsService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //member 생성
                memberService.join("USER", "bigsand");
                memberService.join("USER", "bigsand123");


                //통합검색게시글 생성
                for (int i = 0; i < 100; i++) {
                    searchService.create("평동", "https://img2.joongna.com/media/original/2023/05/23/1684842327279E36_HaZLQ.jpg?impolicy=thumb&size=150",
                            "https://web.joongna.com/product/114619250", 600000, "중고나라", "레노버 게이밍 노트북 (GTX1660ti,144hz)", LocalDateTime.now());
                }

                //인기키워드 생성
                searchService.createSearchKeyword("자전거");
                searchService.createSearchKeyword("의자");
                searchService.createSearchKeyword("냉장고");
                searchService.createSearchKeyword("노트북");
                searchService.createSearchKeyword("모니터");
                searchService.createSearchKeyword("아이패드");
                searchService.createSearchKeyword("스타벅스");
                searchService.createSearchKeyword("책상");


                // 위플리 장터 게시글 생성
                GoodsController.CreateForm createForm = new GoodsController.CreateForm("ㅇㅇ", "지역", Status.구매가능, false, 300, "설명", Collections.emptyList());
                Member member =  memberService.join("USER", "bigsand").getData();
                for(int i = 0; i < 50; i++){
                    goodsService.create(member, createForm);
                }
            }
        };
    }
}
