package com.tidyjava.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class BloggingPlatform {

    public static void main(String[] args) {
        SpringApplication.run(BloggingPlatform.class, args);
    }

    @Bean
    public PathMatchingResourcePatternResolver resourceResolver() {
        return new PathMatchingResourcePatternResolver();
    }
}
