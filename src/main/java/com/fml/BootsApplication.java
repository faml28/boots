package com.fml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("com.fml.mapper")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BootsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootsApplication.class, args);
    }

}
