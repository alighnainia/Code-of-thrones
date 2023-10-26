package com.example.microservicecatalogues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServiceCataloguesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceCataloguesApplication.class, args);
	}

}
