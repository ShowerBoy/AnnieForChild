package com.annie.annieforchild.bean;

import com.annie.annieforchild.bean.material.SubClassList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/4/23.
 */

public class ClassList implements Serializable {
    private String title;
    private int classId;
    private List<SubClassList> subClassList;
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

    public List<SubClassList> getSubClassList() {
        return subClassList;
    }

    public void setSubClassList(List<SubClassList> subClassList) {
        this.subClassList = subClassList;
    }
}
