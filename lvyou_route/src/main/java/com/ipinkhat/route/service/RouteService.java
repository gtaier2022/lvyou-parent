package com.ipinkhat.route.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.entity.UPage;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.domain.*;
import com.ipinkhat.route.dao.RouteDao;
import com.ipinkhat.route.dao.RouteImgDao;
import com.ipinkhat.route.feign.CategoryFeginClient;
import com.ipinkhat.route.feign.CommentFeginClient;
import com.ipinkhat.route.feign.FavoriteFeignClient;
import com.ipinkhat.route.feign.SellerFeginClient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-13:30
 */
@Repository
public class RouteService extends BaseService<Route> {
    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;

    @Override
    public List<Route> findAll() {
        return routeDao.selectList(null);
    }

    @Override
    public void add(Route route) {
        routeDao.insert(route);
    }

    @Override
    public void delete(String id) {
        routeDao.deleteById(id);
    }

    @Override
    public void update(Route route) {
        routeDao.updateById(route);
    }

    @Override
    public Route findOne(String id) {
        return routeDao.selectById(id);
    }

    @Override
    public IPage<Route> findPage(int page, int size) {
      IPage<Route> page1 = new Page<Route>(1,2);
        IPage<Route> routeIPage = routeDao.selectPage(page1, null);
        System.out.println(routeIPage.getRecords());
        return routeIPage;
    }

    @Override
    public IPage<Route> findSearch(Map searchMap, int page, int size) {
        String cid  = (String) searchMap.get("cid");
        String rname  = (String) searchMap.get("cname");
        QueryWrapper<Route> wrapper = new QueryWrapper();
        wrapper.eq(cid!=""&cid!=null,"cid",cid).eq(rname!=""&rname!=null,"rname",rname);
        IPage<Route>iPage = new Page<>(page,size);
        return routeDao.selectPage(iPage,wrapper);

    }
    //根据类型进行查询路线//国内游戏//国外有
    public IPage<Route> getRouteByCid(Integer cid, int currentPage, Integer pageSize) {
        IPage<Route> page = new Page<Route>(currentPage,pageSize);
        QueryWrapper<Route> wrapper = new QueryWrapper<Route>();
        wrapper.eq("cid",cid);
        return routeDao.selectPage(page, wrapper);
    }


    /**
     *    //根据路线名称模糊查询 该类型的查询
     * @param cid
     * @param pageSize
     * @param currentPage
     * @param rname
     * @return
     */
    public IPage<Route> getRouteByCidAndRName(Integer cid, Integer pageSize, Integer currentPage, String rname) {
        IPage<Route> page = new Page<Route>(currentPage,pageSize);
        QueryWrapper<Route>wrapper =new QueryWrapper<Route>();
        wrapper.eq("cid",cid);
        wrapper.like("rname",rname);
        return routeDao.selectPage(page, wrapper);

    }


    /**
     * 首页的主题旅游
     * @return
     */
    public List<Route> findThemeTour() {
        IPage<Route> page = new Page<Route>(0,6);
        QueryWrapper<Route> wrapper =new QueryWrapper<>();
        wrapper.eq("is_theme_tour",1);
        IPage<Route> pageData = routeDao.selectPage(page, wrapper);
        return pageData.getRecords();
    }

    /**
     * 查询流行路线首页
     * @return
     */
    public List<Route> pop() {
        IPage<Route> page = new Page<Route>(0,6);
        QueryWrapper<Route> wrapper = new QueryWrapper<Route>();
        wrapper.orderByDesc("count");
        List<Route> data = routeDao.selectPage(page, wrapper).getRecords();
        return data;
    }

    /**
     * 首页查询最新开启路线
     * @return
     */
    public List<Route> news() {
        IPage<Route>page = new Page<Route>(0,6);
        QueryWrapper<Route>wrapper = new QueryWrapper<Route>();
        wrapper.orderByDesc("rdate");
        return routeDao.selectPage(page, wrapper).getRecords();
    }

    /**
     *首页查询国内游6条
     * @return
     */
    public List<Route> firstPageCN(){
        IPage<Route>page = new Page<Route>(0,6);
        QueryWrapper<Route>wrapper = new QueryWrapper<Route>();
        wrapper.eq("cid",5);
        return routeDao.selectPage(page, wrapper).getRecords();
    }

    /**
     * 首页查询国外游6条
     * @return
     */
    public List<Route> firstPageFN() {
        IPage<Route>page = new Page<Route>(0,6);
        QueryWrapper<Route>wrapper = new QueryWrapper<Route>();
        wrapper.eq("cid",4);
        return routeDao.selectPage(page, wrapper).getRecords();
    }

    /**
     * 首页查询热点旅游8条
     * @return
     */
    public List<Route> hot() {
        Page<Route>page = new Page<Route>(0,8);
        QueryWrapper<Route>wrapper = new QueryWrapper<Route>();
        wrapper.orderByDesc("count");
        List<Route> data = routeDao.selectPage(page, wrapper).getRecords();
        return data;

    }

    /**
     * 按照
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Route> favorite(Integer currentPage, Integer pageSize) {
        Page<Route>page = new Page<Route>(currentPage,pageSize);
        QueryWrapper<Route>wrapper = new QueryWrapper<Route>();
        wrapper.orderByDesc("count");
        return routeDao.selectPage(page, wrapper);
    }

    /**
     * 我收藏的路线
     * @param uid
     * @param current
     * @param size
     * @return
     */
    public Page<Route> myFavRoute(Integer uid,Integer current,Integer size, List<Favorite> list) {
        if (list.size()==0){
            return null;
        }
        List<Integer> rids= new ArrayList<Integer>();
        for (Favorite favorite : list) {
            Integer rid = favorite.getRid();
            rids.add(rid);
        }
        QueryWrapper<Route> wrapper1 = new QueryWrapper<Route>();
        wrapper1.in("rid",rids);
        Page<Route>page = new Page<>(current,size);

        return routeDao.selectPage(page, wrapper1);
    }

    /**
     * 搜索路线
     * @param current
     * @param size
     * @param rname
     * @param lowPirce
     * @param heightPrice
     * @return
     */
    public Page<Route> shouSuo( Integer current, Integer size, String rname, Integer lowPirce, Integer heightPrice) {


        QueryWrapper<Route> wrapper = new QueryWrapper<Route>();
        if (rname==null)rname="";
        wrapper.like("rname",rname);
        if (lowPirce==null)lowPirce=0;
        if (heightPrice==null)heightPrice=100000;
        wrapper.between("price",lowPirce,heightPrice);
        wrapper.orderByDesc("count");
        Page<Route> page = new Page<Route>(current,size);
        Page<Route> data = routeDao.selectPage(page, wrapper);

        List<Route> records = data.getRecords();
        return data;
    }

    /**
     * 根据路线名称解析查询
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public IPage<Route> getRouteByRname(Integer currentPage, Integer pageSize, String rname) {
        Page<Route> page = new Page<Route>(currentPage,pageSize);
        QueryWrapper<Route>wrapper = new QueryWrapper<>();
        wrapper.like("rname",rname);
        return routeDao.selectPage(page,wrapper);
    }

    /**
     * 上传路线
     * @param route
     */
    public void save(Route route,Integer  sid) {
        route.setStatus("0");//未审核
        route.setSid(sid);
        route.setRdate(new Date()+"");
        if (route.getRid()==null) {

            routeDao.insert(route);
        }else {
            routeDao.updateById(route);
        }
    }

    public void updateById(Route route) {
        routeDao.updateById(route);
    }
    //findbyId
    public Route findById(Integer id){
        Route route =   routeDao.selectById(id);
        QueryWrapper<RouteImg> wrapper = new QueryWrapper<>();
        wrapper.eq("rid",id);
        IPage<RouteImg> page = new Page<>(0,4);
        List<RouteImg> routeImgs = routeImgDao.selectPage(page,wrapper).getRecords();
        System.out.println(routeImgs);
        route.setRouteImgs(routeImgs);
         return route;
    }

    public IPage findAllByPageAndSid(UPage page, Integer sid) {
        IPage page1 = new Page(page.getCurrent(),page.getSize());
        QueryWrapper<Route> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",sid);
        IPage page2 = routeDao.selectPage(page1, wrapper);
        return page2;
    }

    public void deleteById(Integer rid) {
        routeDao.deleteById(rid);
    }

    public IPage<Route> findAllByPage(UPage uPage) {
        IPage<Route> page = new Page<>(uPage.getCurrent(),uPage.getSize());
        QueryWrapper<Route> wrapper = new QueryWrapper<>();
        return routeDao.selectPage(page, wrapper);
    }

}
