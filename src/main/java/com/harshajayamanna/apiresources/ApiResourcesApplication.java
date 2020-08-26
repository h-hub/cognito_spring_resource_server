package com.harshajayamanna.apiresources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApiResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiResourcesApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(new String[]{"http://localhost:4200"}).allowedMethods("PUT", "DELETE", "GET", "POST",
						"HEAD");
			}

		};
	}

}
