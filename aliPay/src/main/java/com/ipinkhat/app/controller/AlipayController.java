package com.ipinkhat.app.controller;
import com.alipay.api.AlipayApiException;
import com.ipinkhat.app.feign.OrderFeignClient;
import com.ipinkhat.app.jwt.JwtUtils;
import com.ipinkhat.app.producer.MessageSender;
import com.ipinkhat.app.service.PayService;
import com.ipinkhat.lvyou.domain.AliPayBean;
import com.ipinkhat.lvyou.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("app")
public class AlipayController {
    @Autowired
    private PayService payService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    MessageSender messageSender;
    @RequestMapping(value = "aliPay")
    public String alipay(Integer rid, String token) throws AlipayApiException {


        Order order = orderFeignClient.userOrder(rid, token);

        String totalAmount = order.getTotalAmount().toString();


        AliPayBean alipayBean = new AliPayBean();

        alipayBean.setOut_trade_no(order.getOutTradeNo());
        alipayBean.setSubject(order.getSubject());
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(order.getBody());
        return payService.aliPay(alipayBean);
    }
    @RequestMapping(value = "aliPayOrder")
    public String alipayOrder(Integer id) throws AlipayApiException {
        Order order =orderFeignClient.findById(id);
        AliPayBean alipayBean = new AliPayBean();
        alipayBean.setOut_trade_no(order.getOutTradeNo());
        alipayBean.setSubject(order.getSubject());
        alipayBean.setTotal_amount(order.getTotalAmount().toString());
        alipayBean.setBody(order.getBody());

        return payService.aliPay(alipayBean);
    }
    @RequestMapping(value = "success")
    public void success(@RequestParam String out_trade_no, HttpServletResponse response) throws IOException {
        System.out.println("订单支付成功!");
        System.out.println(out_trade_no);
        Order order =orderFeignClient.selectOne(out_trade_no);
        System.out.println(order);
        order.setStatus(1);
        messageSender.send(order);
        response.sendRedirect("http://localhost:8081/#/payOk");
    }

}
