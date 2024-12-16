package com.lhk.kkojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.lhk")
@MapperScan("com.lhk.kkojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lhk.kkojbackendserviceclient.service"})
@SpringBootApplication
public class KkojBackendUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkojBackendUserServiceApplication.class, args);
	}

}
