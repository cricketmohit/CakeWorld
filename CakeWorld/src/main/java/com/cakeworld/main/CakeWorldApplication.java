package com.cakeworld.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cakeworld.main","com.cakeworld.controller"})
public class CakeWorldApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CakeWorldApplication.class, args);
	}
}
