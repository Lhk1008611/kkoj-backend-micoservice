package com.lhk.kkojbackendjudgeservice;

import com.lhk.kkojbackendjudgeservice.message.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.lhk")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lhk.kkojbackendserviceclient.service"})
@SpringBootApplication
public class KkojBackendJudgeServiceApplication {
	public static void main(String[] args) {
		// 初始化 RabbitMQ
		InitRabbitMq.doInit();
		SpringApplication.run(KkojBackendJudgeServiceApplication.class, args);
	}
}
