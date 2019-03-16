package com.pcp.customer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @depict: 短信消费者
 * @author: PCP
 * @create: 2019-03-06 17:49
 */
@Component
@RabbitListener(queues = "sms")
public class SmsCustomer {

    @RabbitHandler
    public void getSms(Map<String,String> sms){
        System.out.println("手机号："+sms.get("mobile"));
        System.out.println("验证码：："+sms.get("checkCode"));
    }
}
