package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialDetail implements Serializable {
    private String placeholdImg;
    private FirstStage firststage;
    private List<SpecContent> content;

    public String getPlaceholdImg() {
        return placeholdImg;
    }

    public void setPlaceholdImg(String placeholdImg) {
        this.placeholdImg = placeholdImg;
    }

    public FirstStage getFirststage() {
        return firststage;
    }

    public void setFirststage(FirstStage firststage) {
        this.firststage = firststage;
    }

    public List<SpecContent> getContent() {
        return content;
    }

    public void setContent(List<SpecContent> content) {
        this.content = content;
    }
}
