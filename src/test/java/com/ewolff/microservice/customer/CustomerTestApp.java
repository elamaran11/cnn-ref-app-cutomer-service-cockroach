package com.ewolff.microservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CustomerTestApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CustomerTestApp.class);
		app.setAdditionalProfiles("test");
		app.run(args);
	}

}
