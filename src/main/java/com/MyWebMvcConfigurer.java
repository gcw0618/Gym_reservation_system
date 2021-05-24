package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        WebMvcConfigurer adapter=new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/Login").setViewName("login");
                registry.addViewController("/Administrator").setViewName("administrator");
            }
        };
        return adapter;
    }

   /* public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/Loginnn.html","/login","/Administrator.html","/administrator","/Register.html","/goregister","/register","/checklogin","/checkregister");
                
    }*/
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/js/**").addResourceLocations("classpath:/static/","classpath:/js/");
    }*/
}
