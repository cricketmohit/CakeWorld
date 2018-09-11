package com.cakeworld.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cakeworld.controller","com.cakeworld.util.Email"})
@EntityScan("com.cakeworld.model")
public class CakeWorldApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CakeWorldApplication.class, args);
	}
	

}
