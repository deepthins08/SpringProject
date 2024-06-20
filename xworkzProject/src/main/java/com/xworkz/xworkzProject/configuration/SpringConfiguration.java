package com.xworkz.xworkzProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
//      set path for image display
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/Users/Anil/Desktop/ImageUpload/");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(2097152); // 2MB
        return multipartResolver;
    }
}
