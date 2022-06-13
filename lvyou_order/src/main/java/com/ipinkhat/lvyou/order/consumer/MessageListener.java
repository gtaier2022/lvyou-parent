package com.ipinkhat.lvyou.order.consumer;



import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class MessageListener {
	@Autowired
	OrderService orderService;
	//监听binding中的消息
	@StreamListener(Sink.INPUT)
	public void input(Order order) {
		System.out.println("obj"+order);
		orderService.update(order);
	}

}
