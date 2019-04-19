package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/19.
 */

public class VideoFinishBean implements Serializable {
    private int isfinish;
    private int result;

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
