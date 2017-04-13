package com.wncg.news.analysis.monitor.core.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
//@EnableTransactionManagement
@EnableScheduling
//@EnableMongoRepositories
//@EnableAutoConfiguration
//@EnableMongoAuditing
public class SpringConfiguration {

//    @Bean(name = {"transactionManager"})
//    @Value("#{dataSource}")
//    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
//        org.springframework.jdbc.datasource.DataSourceTransactionManager tm = new DataSourceTransactionManager(dataSource);
//        return tm;
//    }


}
