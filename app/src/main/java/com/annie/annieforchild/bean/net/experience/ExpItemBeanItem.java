package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/19.
 */

public class ExpItemBeanItem implements Serializable {
    private String unit_name;
    private String icon_url;
    private String url;
    private String type;
    private String fid;
    private String weeknum;
    private String fsort;
    private String unitid;

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(String weeknum) {
        this.weeknum = weeknum;
    }

    public String getFsort() {
        return fsort;
    }

    public void setFsort(String fsort) {
        this.fsort = fsort;
    }
}
