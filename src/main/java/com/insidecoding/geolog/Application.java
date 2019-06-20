package com.insidecoding.geolog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.spring.context.config.EnableReactor;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties
@EnableReactor
public class Application {

    public static void main(String... strings) {
        SpringApplication.run(Application.class, strings);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }

}
