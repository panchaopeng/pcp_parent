package com.pcp.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @depict: RabbitMQ的消费者启动类
 * @author: PCP
 * @create: 2019-03-06 17:47
 */
@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class);
    }
}
