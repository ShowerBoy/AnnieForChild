package com.annie.annieforchild.bean.material;

/**
 * Created by wanglei on 2018/4/9.
 */

public class MaterialGroup {
    private String title;
    private boolean isSelect;

    public MaterialGroup(String title, boolean isSelect) {
        this.title = title;
        this.isSelect = isSelect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
