package com.ipinkhat.lvyou.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.entity.UPage;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.common.utils.OutTradeNoUtils;
import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.Route;
import com.ipinkhat.lvyou.domain.Seller;
import com.ipinkhat.lvyou.order.dao.OrderDao;
import com.ipinkhat.lvyou.order.feign.RouteFeginClient;
import com.ipinkhat.lvyou.order.feign.SellerFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-15:23
 */
@Service
public class OrderService extends BaseService<Order> {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RouteFeginClient routeFeginClient;
    @Autowired
    private SellerFeginClient sellerFeginClient;
    @Override
    public List<Order> findAll() {
        return orderDao.selectList(null);
    }

    @Override
    public void add(Order order) {
        orderDao.insert(order);
    }

    @Override
    public void delete(String id) {
        orderDao.deleteById(id);
    }

    @Override
    public void update(Order order) {
        orderDao.updateById(order);
    }

    @Override
    public Order findOne(String id) {
        return orderDao.selectById(id);
    }

    @Override
    public IPage<Order> findPage(int page, int size) {
        IPage<Order> orderIPage = new Page<>(page, size);
        return orderDao.selectPage(orderIPage,null);
    }

    @Override
    public IPage<Order> findSearch(Map searchMap, int page, int size) {
        return null;
    }
    //用户添加订单
    public Order userOrder(Integer uid, Integer rid) {
        Route route = routeFeginClient.findById(rid);

        Integer sid = route.getSid();

        Order order = new Order();
        order.setSid(sid+"");
        order.setUid(uid);
        order.setStatus(0);//未支付
        order.setLook("0");//用户可见
        order.setServiceStatus(0);//未服务
        order.setBody(route.getRname());
        order.setOutTradeDate(new Date());
        String outTradeNo = OutTradeNoUtils.getOutTradeNo( rid, uid);
        order.setOutTradeNo(outTradeNo);
        order.setRid(rid);
        order.setSubject(route.getRouteIntroduce());
        order.setTotalAmount(route.getPrice());
        orderDao.insert(order);
        return order;
    }
    //查找用户的订单
    public List<Order> userFindOrder(Integer uid) {
        QueryWrapper<Order>wrapper = new QueryWrapper<Order>();
        wrapper.eq("uid",uid).eq("look","0");
        List<Order> orders = orderDao.selectList(wrapper);
        for (Order order : orders) {
            Integer rid = order.getRid();
            Route one = routeFeginClient.findById(rid);
            order.setRoute(one);
            Seller seller = sellerFeginClient.findById(one.getSid());
            order.setSeller(seller);
        }
        return orders;
    }
    //工具订单号查询订单信息
    public Order selectOne(String out_trade_no) {
        QueryWrapper<Order>wrapper = new QueryWrapper<Order>();
        wrapper.eq("out_trade_no",out_trade_no);
        return orderDao.selectOne(wrapper);

    }
    //删除订单修改订单的用户可见状态
    //用户可见为0
    //用户不可见为1
    public void delete(Integer id) {
        Order order = orderDao.selectById(id);
        order.setLook("1");
        orderDao.updateById(order);
    }

    public Order findById(Integer id) {
        return orderDao.selectById(id);
    }
//添加订单
    //订单号生成规则
    //时间戳+随机数字(6bit)+uid+rid

    public IPage<Order> findAll(IPage<Order> page) {
        return   orderDao.selectPage(page,null);
    }

    public IPage<Order> findOrderBySidAndPage(Integer sid, UPage page) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("sid",sid).eq("look","0").or().eq("look","1");
        IPage<Order> page1 = new Page<Order>(page.getCurrent(),page.getSize()) ;
        return  orderDao.selectPage(page1,wrapper);
    }

    public void updateOrderById(Order order) {
        orderDao.updateById(order);
    }

    public List<Order> findByUidAndRid(String uid, Integer rid) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();

        //商家已经服务完成
        wrapper.eq("uid",uid).eq("rid",rid).eq("service_status",1);
        return  orderDao.selectList(wrapper);
    }

    public void cancelOrder(Integer id) {
        orderDao.deleteById(id);
    }

    public Order findByOutTradeNo(String out_trade_no) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no",out_trade_no);
          return orderDao.selectOne(wrapper);
    }
}
