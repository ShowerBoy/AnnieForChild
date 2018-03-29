package com.annie.annieforchild.bean;

/**
 * 首页推荐
 * Created by wanglei on 2018/3/20.
 */

public class RecommendBean {
    private String name;
    private String imageUrl;
    private int type;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
