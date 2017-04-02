package com.carnival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RefappUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefappUiApplication.class, args);
	}

	@Bean
//	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
