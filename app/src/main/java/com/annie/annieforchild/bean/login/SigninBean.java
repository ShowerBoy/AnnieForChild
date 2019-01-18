package com.annie.annieforchild.bean.login;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

/**
 * Created by wanglei on 2018/7/11.
 */

public class SigninBean extends LitePalSupport {
    private String date;
    private boolean isNectar;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isNectar() {
        return isNectar;
    }

    public void setNectar(boolean nectar) {
        isNectar = nectar;
    }
}
