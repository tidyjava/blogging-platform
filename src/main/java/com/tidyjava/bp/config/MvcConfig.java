package com.tidyjava.bp.config;

import com.tidyjava.bp.git.GitSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private GitSupport gitSupport;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String gitWorkTreePath = gitSupport.getWorkTree().getPath();
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + gitWorkTreePath + "/img/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
