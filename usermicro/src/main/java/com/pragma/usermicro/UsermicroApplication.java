package com.pragma.usermicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EntityScan("com.pragma.usermicro")
@EnableJpaRepositories("com.pragma.usermicro")
public class UsermicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermicroApplication.class, args);
	}

}
