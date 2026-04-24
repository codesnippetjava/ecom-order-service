package org.codesnippet.ecomorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcomOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomOrderServiceApplication.class, args);
    }

}
