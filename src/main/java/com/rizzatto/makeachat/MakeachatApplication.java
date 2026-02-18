package com.rizzatto.makeachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:/Users/guilhermerizzatto/makeachat/env/application.properties")
public class MakeachatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakeachatApplication.class, args);
		System.out.println("SYSTEM SERVICE UP");
	}

}
