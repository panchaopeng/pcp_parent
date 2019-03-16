package com.pcp.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorizeRequests,所有security全注解配置的开端，表示开始说明需要的权限
        //需要的权限分为两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限
        //antMatchers表示拦截什么路径，permitAll表示任何权限都可以访问，放行所有
        //anyRequest表示任何请求，authenticated表示认证后才能访问
        // .and().csrf().disable()，固定写法，表示csrf拦截失效
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
