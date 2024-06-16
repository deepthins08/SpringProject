package com.xworkz.xworkzProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.xworkz.xworkzProject")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class SpringConfiguration implements WebMvcConfigurer {

    public SpringConfiguration(){
        System.out.println("Created SpringConfiguration");
    }

    @Bean
    public ViewResolver viewResolver(){
        System.out.println("Created ViewResolver");
        return new InternalResourceViewResolver("/",".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/js/**").addResourceLocations("/javascript/");
    }
}
