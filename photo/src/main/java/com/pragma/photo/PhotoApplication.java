package com.pragma.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableSwagger2
@SpringBootApplication
public class PhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoApplication.class, args);
	}

}
