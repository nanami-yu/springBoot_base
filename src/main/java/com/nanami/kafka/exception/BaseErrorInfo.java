package com.nanami.kafka.exception;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 异常处理接口类
 * @date 2020/12/1 10:13
 */
public interface BaseErrorInfo {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
