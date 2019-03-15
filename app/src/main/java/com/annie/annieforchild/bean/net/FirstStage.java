package com.annie.annieforchild.bean.net;

import com.annie.annieforchild.bean.net.netexpclass.InfoSpec;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class FirstStage implements Serializable {
    private String fcategoryname;
    private List<InfoSpec> info;

    public String getFcategoryname() {
        return fcategoryname;
    }

    public void setFcategoryname(String fcategoryname) {
        this.fcategoryname = fcategoryname;
    }

    public List<InfoSpec> getInfo() {
        return info;
    }

    public void setInfo(List<InfoSpec> info) {
        this.info = info;
    }
}
