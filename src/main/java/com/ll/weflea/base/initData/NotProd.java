package com.ll.weflea.base.initData;

import com.ll.weflea.boundedContext.member.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(MemberService memberService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //member 생성
                memberService.create("USER", "son", "bigsand", "sadf@gmail.com", "kakao");
                memberService.create("USER", "son123", "bigsand123", "sadf123@gmail.com", "kakao");

                //게시글 생성

            }
        };
    }
}
