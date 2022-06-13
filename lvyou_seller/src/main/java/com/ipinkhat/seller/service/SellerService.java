package com.ipinkhat.seller.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.domain.Seller;
import com.ipinkhat.seller.dao.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-15:52
 */
@Service
public class SellerService extends BaseService<Seller> {
    @Autowired
    private SellerDao sellerDao;
    @Override
    public List<Seller> findAll() {
        return sellerDao.selectList(null);
    }

    @Override
    public void add(Seller seller) {
         sellerDao.insert(seller);

    }

    @Override
    public void delete(String id) {
        sellerDao.deleteById(id);
    }

    @Override
    public void update(Seller seller) {
        sellerDao.updateById(seller);
    }

    @Override
    public Seller findOne(String id) {
        return sellerDao.selectById(id);
    }

    @Override
    public IPage<Seller> findPage(int page, int size) {
        return sellerDao.selectPage(new Page<>(page, size),null);
    }

    @Override
    public IPage<Seller> findSearch(Map searchMap, int page, int size) {
        return sellerDao.selectPage(new Page<>(page, size),null);
    }
}
