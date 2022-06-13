package com.ipinkhat.app.service;

import com.alipay.api.AlipayApiException;
import com.ipinkhat.lvyou.domain.AliPayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname: PayService
 * @Auther: Created by 张文 on 2021/12/28.
 * @Description:
 */
@Service
public class PayService {
    @Autowired
    private Alipay alipay;
    public String aliPay(AliPayBean aliPayBean)throws AlipayApiException {
        return alipay.pay(aliPayBean);
    }
}
