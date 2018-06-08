package com.annie.annieforchild.bean.tongzhi;

import java.util.List;

/**
 * Created by wanglei on 2018/6/5.
 */

public class MyNotice {
    private List<Notice> notis;
    private List<Notice> messages;

    public List<Notice> getNotis() {
        return notis;
    }

    public void setNotis(List<Notice> notis) {
        this.notis = notis;
    }

    public List<Notice> getMessages() {
        return messages;
    }

    public void setMessages(List<Notice> messages) {
        this.messages = messages;
    }
}
