package com.ipinkhat.route.feign;

import com.ipinkhat.lvyou.domain.Category;
import com.ipinkhat.lvyou.domain.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name="lvyou-comment")
public interface CommentFeginClient {
    @RequestMapping(value = "/comment/findByLookStatus/{rid}",method = RequestMethod.GET)
    List<Comment> findById(@PathVariable("rid") Integer rid);

}
