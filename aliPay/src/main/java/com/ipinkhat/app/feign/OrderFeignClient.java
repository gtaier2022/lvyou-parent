package com.ipinkhat.app.feign;

import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/17-20:52
 */
@Component
@FeignClient(name="lvyou-order")
public interface OrderFeignClient {
    @RequestMapping(value = "/order/userOrderFeign/{token}/{rid}",method = RequestMethod.GET)
     Order userOrder(@PathVariable("rid") Integer rid,@PathVariable("token")String token);
    @RequestMapping(value = "/order/findByOutTradeNo/{out_trade_no}",method = RequestMethod.GET)
    Order selectOne(@PathVariable("out_trade_no") String out_trade_no);
    @RequestMapping(value = "/order/findById/{id}",method = RequestMethod.GET)
    Order findById(@PathVariable("id")Integer id);
}
