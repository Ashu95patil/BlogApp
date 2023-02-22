package com.codewithAshu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

public class SwaggerConfig {

	@Bean
	public Docket apiDoc() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo())

				.select().apis(RequestHandlerSelectors.basePackage("com.codewithAshu.blog")).paths(PathSelectors.any())
				.build();
	}

	@SuppressWarnings("deprecation")
	private ApiInfo getInfo() {

		return new ApiInfo("Ashwini Swagger", "This project is devloped by HappyTech Company", "version 1.0", "pune",
				"HappyTech", "patilashwini566@gmail.com", "NextPage Name");
	}

}
