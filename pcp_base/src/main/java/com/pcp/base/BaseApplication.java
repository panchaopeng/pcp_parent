package com.pcp.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @depict: 创建启动类
 * @author: PCP
 * @create: 2019-02-21 22:40
 */

@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * @depict: 数据库使用前要装载分布式id生成器到容器中
     * @return: util.IdWorker {@link IdWorker}
     * @author: PCP
     * @create: 2019/2/21 22:48
     */
    @Bean //把返回对象装进容器里
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
