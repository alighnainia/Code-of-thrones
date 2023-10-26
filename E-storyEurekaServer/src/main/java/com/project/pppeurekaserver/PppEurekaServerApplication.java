package com.project.pppeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer

@SpringBootApplication
public class PppEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PppEurekaServerApplication.class, args);
	}

}
