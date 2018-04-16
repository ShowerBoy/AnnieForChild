package com.annie.annieforchild.bean.song;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/3/23.
 */

public class SongClassify implements Serializable {
    private String title;
    private int calssId;
    private boolean isSelected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalssId() {
        return calssId;
    }

    public void setCalssId(int calssId) {
        this.calssId = calssId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
