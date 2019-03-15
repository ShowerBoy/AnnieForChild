package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/3/13.
 */

public class InfoSpec implements Serializable {
    private String name;
    private String url;
    private String fid;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
