package com.annie.annieforchild.bean.material;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/4/24.
 */

public class SubClassList implements Serializable {
    private String title;
    private int classId;
    private List<Material> materialList;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }
}
