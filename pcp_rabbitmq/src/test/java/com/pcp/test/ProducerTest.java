package com.pcp.test;

import com.pcp.rabbit.RabbitMQApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @depict: 消息生产者
 * @author: PCP
 * @create: 2019-03-06 14:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("itcast","直接模式producer");
    }

    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("ex_it","","分裂模式");
    }

    @Test
    public void sendMsg3(){
        rabbitTemplate.convertAndSend("topic_pcp","abc.log","主题模式");
    }
}
