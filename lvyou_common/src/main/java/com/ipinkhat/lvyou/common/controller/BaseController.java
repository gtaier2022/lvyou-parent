package com.ipinkhat.lvyou.common.controller;

import com.ipinkhat.lvyou.common.entity.Result;

import java.util.List;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-14:30
 */
public interface  BaseController<T> {
    Result findAll();
    Result save(T t);
    Result deleteById(Long id);
    Result updateById(T t);
    Result findById(Long id);
}
