package com.annie.annieforchild.bean.login;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

/**
 * 登陆成功返回bean
 * Created by WangLei on 2018/2/5 0005
 */

public class LoginBean extends LitePalSupport {
    private String token;
    private String defaultUsername;

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
