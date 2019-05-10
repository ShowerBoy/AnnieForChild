package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDetailBean implements Serializable{
    private int num;
    private String title;
    private List<String> images;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
