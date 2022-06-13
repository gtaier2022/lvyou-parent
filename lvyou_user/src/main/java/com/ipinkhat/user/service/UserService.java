package com.ipinkhat.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.domain.User;
import com.ipinkhat.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-15:58
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        return userDao.selectList(null);
    }


    public void add(User user) {
        userDao.insert(user);

    }


    public void delete(String id) {
        userDao.deleteById(id);
    }


    public void update(User user) {
        userDao.updateById(user);
    }


    public User findOne(String id) {
        return userDao.selectById(id);
    }


    public IPage<User> findPage(int page, int size) {
        return userDao.selectPage(new Page<>(page,size),null);
    }


    public IPage<User> findSearch(Map searchMap, int page, int size) {
        return userDao.selectPage(new Page<>(page,size),null);

    }

    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
       return userDao.selectOne(wrapper);
    }

    public void insert(User user) {
        userDao.insert(user);
    }

    public User findByUid(int uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        return userDao.selectOne(wrapper);
    }

    public User findByTelephone(String telephone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("telephone",telephone);
       return userDao.selectOne(wrapper);
    }
}
