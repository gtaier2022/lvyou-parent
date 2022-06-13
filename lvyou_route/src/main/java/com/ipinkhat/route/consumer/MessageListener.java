package com.ipinkhat.route.consumer;

import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.Route;
import com.ipinkhat.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/16-13:22
 */
@Component
@EnableBinding(Sink.class)
public class MessageListener {
    @Autowired
    private RouteService routeService;
    @StreamListener(Sink.INPUT)
    public void receive(Route route){
    routeService.updateById(route);
    }
}
