package com.manu.adminpannel.config;

import com.manu.adminpannel.ratelimit.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/user/me", "/admin/**"); 
        
    }
}
