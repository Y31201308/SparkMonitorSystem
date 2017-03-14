package com.wncg.news.analysis.monitor.core.springconfig;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)                         {
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

}
