package com.ipinkhat.route.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ipinkhat.lvyou.domain.Route;
import com.ipinkhat.lvyou.domain.RouteImg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/15-22:08
 */
@Mapper
@Repository
public interface  RouteImgDao extends BaseMapper<RouteImg> {
}
