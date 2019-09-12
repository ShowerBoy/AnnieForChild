package com.annie.annieforchild.bean.song;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/3/23.
 */

public class SongClassify implements Serializable {
    private String title;
    private String classId;
    private boolean isSelected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
