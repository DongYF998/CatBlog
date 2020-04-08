package com.blog.cat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@MapperScan(basePackages = {"com.blog.cat.dao"}, annotationClass = Repository.class)
@EnableConfigurationProperties
public class CatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatApplication.class, args);
    }

}
