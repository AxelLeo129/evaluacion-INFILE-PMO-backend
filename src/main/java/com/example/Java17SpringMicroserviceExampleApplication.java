package com.example;

import com.example.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the EvaluacionInfilePmoBackendAuth application.
 * <p>
 * This class configures and starts the Spring Boot application. It also registers an authentication filter
 * for specific API endpoints.
 * </p>
 */
@SpringBootApplication
public class Java17SpringMicroserviceExampleApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Java17SpringMicroserviceExampleApplication.class, args);
	}

	/**
	 * Registers the authentication filter for the API endpoints.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/news/*");
		return registrationBean;
	}

}
