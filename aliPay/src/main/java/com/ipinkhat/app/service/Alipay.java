package com.ipinkhat.app.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ipinkhat.lvyou.domain.AliPayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname: AliPay
 * @Auther: Created by 张文 on 2021/12/28.
 * @Description:
 */
@Component
public class Alipay {

    @Autowired
    private AlipayApp app;

    public String pay(AliPayBean aliPayBean) throws AlipayApiException {
        System.out.println(app);
        //1.初始化AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(app.getGatewayUrl(),
                app.getAppId(),
                app.getPrivateKey(),
                "json",
                app.getCharset(),
                app.getPublicKey(),
                app.getSignType());
        //2.设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(app.getReturnUrl());
        alipayRequest.setNotifyUrl(app.getNotifyUrl());
        alipayRequest.setBizContent(JSON.toJSONString(aliPayBean));
        //3.请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //4.返回付款结果
        return result;
    }
}
