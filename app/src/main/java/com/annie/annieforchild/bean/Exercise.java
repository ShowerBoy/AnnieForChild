package com.annie.annieforchild.bean;

/**
 * Created by wanglei on 2018/3/30.
 */

public class Exercise {
    private String text;
    private boolean isSelect;

    public Exercise(String text, boolean isSelect) {
        this.text = text;
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
