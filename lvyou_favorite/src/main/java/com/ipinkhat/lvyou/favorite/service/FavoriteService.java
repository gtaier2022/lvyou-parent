package com.ipinkhat.lvyou.favorite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.domain.Favorite;
import com.ipinkhat.lvyou.favorite.dao.FavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-15:14
 */
@Service
public class FavoriteService extends BaseService<Favorite> {
    @Autowired
    private FavoriteDao favoriteDao;


    @Override
    public List<Favorite> findAll() {
        return favoriteDao.selectList(null);
    }

    @Override
    public void add(Favorite favorite) {
favoriteDao.insert(favorite);
    }

    @Override
    public void delete(String id) {
        favoriteDao.deleteById(id);
    }

    @Override
    public void update(Favorite favorite) {
        favoriteDao.updateById(favorite);
    }

    @Override
    public Favorite findOne(String id) {
        return favoriteDao.selectById(id);
    }

    @Override
    public IPage<Favorite> findPage(int page, int size) {
        return favoriteDao.selectPage(new Page<Favorite>(page, size),null);
    }

    @Override
    public IPage<Favorite> findSearch(Map searchMap, int page, int size) {
        return favoriteDao.selectPage(new Page<Favorite>(page, size),null);

    }

    //查询收藏加内有无该路线
    public Favorite findOneFavorite(Integer uid, Integer rid) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<Favorite>();
        wrapper.eq("uid",uid).eq("rid",rid);

        return favoriteDao.selectOne(wrapper);



    }

    public List<Favorite> findByUid(Integer uid) {
        QueryWrapper<Favorite> wrapper  = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        return  favoriteDao.selectList(wrapper);
    }

    public void addFavorite(Favorite favorite) {
        favoriteDao.insert(favorite);
    }

    public void deleteFavorite(String fid) {
        favoriteDao.deleteById(fid);
    }
    public List<Favorite> findByUid(Long uid){
        QueryWrapper<Favorite>wrapper = new QueryWrapper<Favorite>();
        wrapper.orderByDesc("date");
        wrapper.eq("uid",uid);
        return favoriteDao.selectList(wrapper);
    }
}
