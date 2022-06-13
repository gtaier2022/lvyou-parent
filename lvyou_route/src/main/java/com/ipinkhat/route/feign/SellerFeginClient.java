package com.ipinkhat.route.feign;

import com.ipinkhat.lvyou.domain.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name="lvyou-seller")
public interface SellerFeginClient {
    @RequestMapping(value = "/seller/{id}",method = RequestMethod.GET)
    Seller findById(@PathVariable("id") Integer id);

}
