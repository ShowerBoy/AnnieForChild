package com.annie.annieforchild.bean.login;

import org.litepal.crud.LitePalSupport;

/**
 * 登陆成功返回bean
 * Created by WangLei on 2018/2/5 0005
 */

public class LoginBean extends LitePalSupport {
    private String token;
    private String defaultUsername;
    private int parentid;

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }
}
