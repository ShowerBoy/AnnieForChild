package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class SpecInfo implements Serializable {
    private String fcategoryname;
    private String fparentid;
    private String fsort;
    private List<SpecInfoInfo> info;

    public String getFcategoryname() {
        return fcategoryname;
    }

    public void setFcategoryname(String fcategoryname) {
        this.fcategoryname = fcategoryname;
    }

    public String getFparentid() {
        return fparentid;
    }

    public void setFparentid(String fparentid) {
        this.fparentid = fparentid;
    }

    public String getFsort() {
        return fsort;
    }

    public void setFsort(String fsort) {
        this.fsort = fsort;
    }

    public List<SpecInfoInfo> getInfo() {
        return info;
    }

    public void setInfo(List<SpecInfoInfo> info) {
        this.info = info;
    }
}
