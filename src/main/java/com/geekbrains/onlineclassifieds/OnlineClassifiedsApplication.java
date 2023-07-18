package com.geekbrains.onlineclassifieds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineClassifiedsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineClassifiedsApplication.class, args);
    }

}
