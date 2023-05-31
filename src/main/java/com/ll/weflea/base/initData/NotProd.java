package com.ll.weflea.base.initData;

import com.ll.weflea.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(MemberService memberService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //member 생성
                memberService.join("USER", "bigsand");
                memberService.join("USER", "bigsand123");


                //게시글 생성

            }
        };
    }
}
