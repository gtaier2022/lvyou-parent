package com.ipinkhat.lvyou.favorite.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/16-13:16
 */
@Component
@EnableBinding(Source.class )
public class MessageSender {
    @Autowired
    private MessageChannel output;
    public void send(Object obj){
     output.send(MessageBuilder.withPayload(obj).build());
    }
}
