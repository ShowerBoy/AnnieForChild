package com.annie.annieforchild.bean.radio;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/12/13.
 */

public class RadioTag implements Serializable {
    private int radioId;
    private String radioTitle;
    private String radioImageUrl;

    public int getRadioId() {
        return radioId;
    }

    public void setRadioId(int radioId) {
        this.radioId = radioId;
    }

    public String getRadioTitle() {
        return radioTitle;
    }

    public void setRadioTitle(String radioTitle) {
        this.radioTitle = radioTitle;
    }

    public String getRadioImageUrl() {
        return radioImageUrl;
    }

    public void setRadioImageUrl(String radioImageUrl) {
        this.radioImageUrl = radioImageUrl;
    }
}
