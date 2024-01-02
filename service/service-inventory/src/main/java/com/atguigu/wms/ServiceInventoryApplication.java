package com.atguigu.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceInventoryApplication.class, args);
	}

	/**
	 * restTemplate对象初始化
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
