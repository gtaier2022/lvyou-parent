package com.ipinkhat.route.feign;

import com.ipinkhat.lvyou.domain.Category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name="lvyou-category")
public interface CategoryFeginClient {
    @RequestMapping(value = "/category/{id}",method = RequestMethod.GET)
    Category  findById(@PathVariable("id") Integer id);

}
