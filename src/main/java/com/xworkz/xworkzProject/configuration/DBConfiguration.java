package com.xworkz.xworkzProject.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Slf4j
@EnableTransactionManagement
public class DBConfiguration {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.user}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.hikari.maximum-pool-size:10}")
    private int maximumPoolSize;
    @Value("${jdbc.hikari.minimum-idle:5}")
    private int minimumIdle;
    @Value("${jdbc.hikari.idle-timeout:30000}")
    private long idleTimeout;
    @Value("${jdbc.hikari.pool-name:MyHikariCP}")
    private String poolName;
    @Value("${jdbc.hikari.max-lifetime:1800000}")
    private long maxLifetime;
    @Value("${jdbc.hikari.connection-timeout:30000}")
    private long connectionTimeout;
    @Value("${jdbc.hikari.leak-detection-threshold:2000}")
    private long leakDetectionThreshold;

    DBConfiguration(){
        log.info("Created DBConfiguration");
    }

    @Bean
    public DataSource dataSource(){
        log.info("Data Source is created");
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

//    @Bean
//    public DataSource dataSource() {
//        System.out.println("Data Source is created using HikariCP");
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName(driver);
//        hikariConfig.setJdbcUrl(url);
//        hikariConfig.setUsername(userName);
//        hikariConfig.setPassword(password);
//        hikariConfig.setMaximumPoolSize(maximumPoolSize);
//        hikariConfig.setMinimumIdle(minimumIdle);
//        hikariConfig.setIdleTimeout(idleTimeout);
//        hikariConfig.setPoolName(poolName);
//        hikariConfig.setMaxLifetime(maxLifetime);
//        hikariConfig.setConnectionTimeout(connectionTimeout);
//        hikariConfig.setLeakDetectionThreshold(leakDetectionThreshold);
//
//        return new HikariDataSource(hikariConfig);
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource){
        log.info("Created LocalContainerEntityManagerFactoryBean");
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean=new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.xworkz");
        JpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        return entityManagerFactoryBean;
    }


}
