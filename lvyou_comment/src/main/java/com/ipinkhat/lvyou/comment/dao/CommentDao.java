package com.ipinkhat.lvyou.comment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ipinkhat.lvyou.domain.Category;
import com.ipinkhat.lvyou.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-13:30
 */
@Repository
@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
