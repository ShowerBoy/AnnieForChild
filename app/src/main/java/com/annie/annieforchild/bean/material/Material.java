package com.annie.annieforchild.bean.material;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/4/24.
 */

public class Material implements Serializable{
    private int materialId;
    private String name;
    private String imageUrl;

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

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
}
