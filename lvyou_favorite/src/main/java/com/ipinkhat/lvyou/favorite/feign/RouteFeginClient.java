package com.ipinkhat.lvyou.favorite.feign;

import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Component
@FeignClient(name="lvyou-route")
public interface RouteFeginClient {
    @RequestMapping(value = "/route/{id}",method = RequestMethod.GET)
    public Route findById(@PathVariable("id") Integer id);

}
