package com.pcp.user.config;

import com.pcp.user.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @depict: 注册JWT拦截器对象
 * @author: PCP
 * @create: 2019-03-11 20:17
 */
@Configuration
public class JWTInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要声明拦截器对象和要拦截的请求
        //addPathPatterns("/**") 拦截所有请求
        //excludePathPatterns("/**/login/**") 不拦截login请求，因为login内要生成Token
        registry
                .addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
