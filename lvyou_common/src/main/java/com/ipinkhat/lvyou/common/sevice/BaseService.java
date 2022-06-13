package com.ipinkhat.lvyou.common.sevice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.domain.Comment;

import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-13:36
 */
public  class     BaseService<T> {
  public   List<T>  findAll(){
        return null;
    };
    public void add(T t){

    };
    public    void delete(String id){

    };
    public   void update(T t){

    };
    public   T findOne(String id){
        return null;
    };

    public   IPage<T> findPage(int page, int size){
        return null;
    };

    public   IPage<T> findSearch(Map searchMap, int page, int size){
        return null;
    };
}
