package com.ipinkhat.app.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname: AlipayApp
 * @Auther: Created by 张文 on 2021/12/28.
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AlipayApp {

    /**
     * appId
     */
    private String appId;
    /**
     * 商户私钥
     */
    private String privateKey;
    /**
     * 支付宝公钥
     */
    private String publicKey;
    /**
     * 服务器异步通知页面路径，需要公网能访问到
     */
    private String notifyUrl;
    /**
     * 服务器同步通知页面路径，需要公网能访问到
     */
    private String returnUrl;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 字符编码格式
     */
    private String charset;
    /**
     * 支付宝网关
     */
    private String gatewayUrl;
}

