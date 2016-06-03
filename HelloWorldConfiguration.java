package com.websystique.springmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.websystique.springmvc")
public class HelloWorldConfiguration {

    @Bean
    public DataSource dataSource() {
        DataSource bean = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).addScript("classpath:schema.sql").build();
        return bean;
    }
}
