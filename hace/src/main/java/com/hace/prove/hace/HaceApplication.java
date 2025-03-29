package com.hace.prove.hace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.hace.prove.hace")
public class HaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaceApplication.class, args);
	}

}



