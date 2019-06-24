package com.annie.annieforchild.bean.child;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/6/23.
 */

public class ChildBean implements Serializable {
    private int result;
    private String username;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
