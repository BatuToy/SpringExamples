package com.btoy.mapperExample.mapperDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.btoy.mapperExample.mapperDemo.repository")
public class MapperDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapperDemoApplication.class, args);
	}

}
