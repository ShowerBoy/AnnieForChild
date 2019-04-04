package com.annie.annieforchild.bean.order;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/4.
 */

public class SecretaryInfo implements Serializable {
    private String teacher;
    private String nikename;
    private String title;
    private String tips;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
