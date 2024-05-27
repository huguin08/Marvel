package com.marvel.marvel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.marvel.marvel")
public class MarvelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelApplication.class, args);
	}

}
