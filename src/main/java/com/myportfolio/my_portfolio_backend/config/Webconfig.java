package com.myportfolio.my_portfolio_backend.config;

import com.myportfolio.my_portfolio_backend.interceptor.CalendarInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    private final CalendarInterceptor calendarInterceptor;

    public Webconfig(CalendarInterceptor calendarInterceptor) {
        this.calendarInterceptor = calendarInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(calendarInterceptor)
                .addPathPatterns("/form");
    }
}
