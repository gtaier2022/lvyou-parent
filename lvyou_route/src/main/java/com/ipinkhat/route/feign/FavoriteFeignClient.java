package com.ipinkhat.route.feign;

import com.ipinkhat.lvyou.domain.Category;
import com.ipinkhat.lvyou.domain.Favorite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name="lvyou-favorite")
public interface FavoriteFeignClient {
    @RequestMapping(value = "/favorite/findByUid/{uid}",method = RequestMethod.GET)
    List<Favorite> findById(@PathVariable("uid") Integer uid);

}
