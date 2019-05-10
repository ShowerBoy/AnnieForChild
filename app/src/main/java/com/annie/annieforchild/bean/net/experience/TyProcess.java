package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/5/8.
 */

public class TyProcess implements Serializable {
    private int isshow;
    private String image;
    private String title;
    private String url;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
