package com.ipinkhat.lvyou.comment.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 负责向中间件发送数据
 */
@Component
@EnableBinding(Source.class)
public class MessageSender {

	@Autowired
	@Qualifier(value="output")
	private MessageChannel output;

	//发送消息
	public void send(Object obj) {
		output.send(MessageBuilder.withPayload(obj).build());
	}
}
