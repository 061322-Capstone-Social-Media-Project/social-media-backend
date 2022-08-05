package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
	}

	@Component
	public static class CorsFix implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:4200", "http://127.0.0.1:4200", "http://ec2-3-138-189-139.us-east-2.compute.amazonaws.com:4200")
					.allowedHeaders("*")
					.allowCredentials(true)
					.exposedHeaders("Authorization")
					.allowedMethods("GET", "OPTIONS", "PUT", "POST", "DELETE");
		}
	}
}
