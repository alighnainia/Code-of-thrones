package com.example.microservicecoursprive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServiceCoursPriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceCoursPriveApplication.class, args);
	}

}
