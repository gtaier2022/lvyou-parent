package com.ipinkhat.lvyou.comment.feign;

import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="lvyou-user")
public interface UserFeginClient {
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id);

}
