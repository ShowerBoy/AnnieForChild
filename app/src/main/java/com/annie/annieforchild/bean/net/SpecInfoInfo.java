package com.annie.annieforchild.bean.net;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/3/13.
 */

public class SpecInfoInfo implements Serializable {
    private String fcategoryname;
    private String fid;
    private String type;

    public String getFcategoryname() {
        return fcategoryname;
    }

    public void setFcategoryname(String fcategoryname) {
        this.fcategoryname = fcategoryname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
