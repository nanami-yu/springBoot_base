package com.nanami.kafka.dto;

import java.util.Date;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 发送信息类
 * @date 2020/11/23 10:41
 */
public class Message {
    private String id;

    private String msg;

    private Date sendTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
