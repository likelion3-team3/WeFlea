package com.ll.weflea.base.config;

import com.ll.weflea.base.interceptor.NicknameCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final NicknameCheckInterceptor nicknameCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(nicknameCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/member/login", "/css/**", "/resource/**", "/starboot/**", "/vendor/**", "/user/member/update/nickname");
    }
}
