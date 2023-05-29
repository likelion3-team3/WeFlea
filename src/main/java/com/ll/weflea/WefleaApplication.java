package com.ll.weflea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 기본 로그인 화면 임시 제거
@EnableJpaAuditing
public class WefleaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WefleaApplication.class, args);
	}

}
