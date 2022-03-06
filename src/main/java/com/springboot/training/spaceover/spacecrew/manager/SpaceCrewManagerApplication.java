package com.springboot.training.spaceover.spacecrew.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpaceCrewManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceCrewManagerApplication.class, args);
    }

}
