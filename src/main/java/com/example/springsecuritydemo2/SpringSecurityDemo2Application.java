package com.example.springsecuritydemo2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@MapperScan("com.example.springsecuritydemo2.dao")
@SpringBootApplication
@EnableAutoConfiguration
public class SpringSecurityDemo2Application {

	/**
	 * 自定义异常页
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/Login401");
			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/Login403");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/Login404");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/Login500");
			container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemo2Application.class, args);
	}
}
