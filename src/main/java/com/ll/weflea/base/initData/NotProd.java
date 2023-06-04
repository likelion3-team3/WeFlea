package com.ll.weflea.base.initData;

import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.search.service.SearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(MemberService memberService, SearchService searchService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //member 생성
                memberService.join("USER", "bigsand");
                memberService.join("USER", "bigsand123");


                //통합검색게시글 생성
                for (int i = 0; i < 1000; i++) {
                    searchService.create("평동", "https://img2.joongna.com/media/original/2023/05/23/1684842327279E36_HaZLQ.jpg?impolicy=thumb&size=150",
                            "https://web.joongna.com/product/114619250", "600,000원", "중고나라", "레노버 게이밍 노트북 (GTX1660ti,144hz)");
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
            }
        };
    }
}
