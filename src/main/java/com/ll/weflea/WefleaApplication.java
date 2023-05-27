package com.ll.weflea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WefleaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WefleaApplication.class, args);
	}

}
