package com.annie.annieforchild.bean.login;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

/**
 * 手机信息bean
 * Created by WangLei on 2018/1/30 0030
 */

public class PhoneSN extends LitePalSupport {
    private String bitcode;
    private String system;
    private String sn;
    private String username;
    private String lastlogintime;

    public PhoneSN() {
    }

    public PhoneSN(String bitcode, String system, String sn, String username, String lastlogintime) {
        this.bitcode = bitcode;
        this.system = system;
        this.sn = sn;
        this.username = username;
        this.lastlogintime = lastlogintime;
    }

    public String getBitcode() {
        return bitcode;
    }

    public void setBitcode(String bitcode) {
        this.bitcode = bitcode;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "PhoneSN{" +
                "bitcode='" + bitcode + '\'' +
                ", system='" + system + '\'' +
                ", sn='" + sn + '\'' +
                ", username='" + username + '\'' +
                ", lastlogintime='" + lastlogintime + '\'' +
                '}';
    }
}
