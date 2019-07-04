package com.yunze.vehiclemanagement.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 配置事务管理器
 */

//开启事务管理
@EnableTransactionManagement
@Configuration
public class TransactionManagementConfig {

    @Bean
    public PlatformTransactionManager platformTransactionManager(
            @Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}