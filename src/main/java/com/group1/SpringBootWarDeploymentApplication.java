package com.group1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootWarDeploymentApplication extends SpringBootServletInitializer {
	
	public static void main(String args) {
		org.springframework.boot.SpringApplication.run(SpringBootWarDeploymentApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootWarDeploymentApplication.class);
	}
}
