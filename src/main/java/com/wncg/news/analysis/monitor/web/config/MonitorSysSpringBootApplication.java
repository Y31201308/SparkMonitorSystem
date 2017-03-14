package com.wncg.news.analysis.monitor.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories
//@EnableMongoAuditing
@EnableAutoConfiguration
//@EnableJpaRepositories
@ComponentScan(basePackages = {"com.wncg.news.analysis.monitor"})
public class MonitorSysSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorSysSpringBootApplication.class, args);
    }
}