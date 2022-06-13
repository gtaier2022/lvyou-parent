package com.ipinkhat.lvyou.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/15-14:29
 */
public    final  class OutTradeNoUtils {

    public  static String  getOutTradeNo(Integer rid, Integer uid){

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsss");
      String out =  format.format(new Date())+new Random().nextInt(2)+rid+uid+new Random().nextInt(2);
        System.out.println(out);
        return out;

    }

}
