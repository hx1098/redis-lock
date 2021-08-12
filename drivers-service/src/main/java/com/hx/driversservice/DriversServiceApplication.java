package com.hx.driversservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @ClassName DriversServiceApplication
 * @Description   司机端
 * @Author hx
 * @Date 2021/8/12 17:52 
 * @Version 1.0
 */
@EnableAsync
@EnableEurekaClient
@SpringBootApplication
public class DriversServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriversServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
