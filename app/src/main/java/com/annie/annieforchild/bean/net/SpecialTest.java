package com.annie.annieforchild.bean.net;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/3/14.
 */

public class SpecialTest implements Serializable {
    private String fchaptername;
    private String ftesturl;

    public String getFchaptername() {
        return fchaptername;
    }

    public void setFchaptername(String fchaptername) {
        this.fchaptername = fchaptername;
    }

    public String getFtesturl() {
        return ftesturl;
    }

    public void setFtesturl(String ftesturl) {
        this.ftesturl = ftesturl;
    }
}
