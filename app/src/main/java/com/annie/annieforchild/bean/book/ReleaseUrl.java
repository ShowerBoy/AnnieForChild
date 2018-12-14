package com.annie.annieforchild.bean.book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/3.
 */

public class ReleaseUrl implements Serializable {
    private Boolean tag;
    private List<Integer> nectarList; //判断增加花蜜
    private String myResourseUrl;

    public ReleaseUrl(Boolean tag, List<Integer> nectarList, String myResourseUrl) {
        this.tag = tag;
        this.nectarList = nectarList;
        this.myResourseUrl = myResourseUrl;
    }

    public List<Integer> getNectarList() {
        return nectarList;
    }

    public void setNectarList(List<Integer> nectarList) {
        this.nectarList = nectarList;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public String getMyResourseUrl() {
        return myResourseUrl;
    }

    public void setMyResourseUrl(String myResourseUrl) {
        this.myResourseUrl = myResourseUrl;
    }
}
