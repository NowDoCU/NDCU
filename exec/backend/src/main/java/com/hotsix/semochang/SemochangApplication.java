package com.hotsix.semochang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SemochangApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemochangApplication.class, args);
    }
}
