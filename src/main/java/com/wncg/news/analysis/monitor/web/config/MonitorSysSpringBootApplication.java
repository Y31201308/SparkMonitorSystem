package com.wncg.news.analysis.monitor.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableCaching
//@EnableMongoRepositories
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.wncg.news.analysis.monitor"})
public class MonitorSysSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorSysSpringBootApplication.class, args);
    }
}