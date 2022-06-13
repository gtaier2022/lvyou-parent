package com.ipinkhat.lvyou.common.exception;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/15-18:12
 */
public class CommonException extends Exception  {
    private Integer code;
    private String errorMsg;
    public CommonException(Integer code, String errorMsg){
        this.code =code;
        this.errorMsg = errorMsg;

    }
}
