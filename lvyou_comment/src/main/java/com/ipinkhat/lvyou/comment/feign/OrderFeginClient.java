package com.ipinkhat.lvyou.comment.feign;

import com.ipinkhat.lvyou.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="lvyou-order")
public interface OrderFeginClient {
    @RequestMapping(value = "/order/findById/{id}",method = RequestMethod.GET)
    public Order findById(@PathVariable("id") Long id);

}
