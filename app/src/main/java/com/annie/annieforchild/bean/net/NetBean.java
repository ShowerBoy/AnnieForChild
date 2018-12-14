package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetBean implements Serializable {
    private String teacherName;
    private String teacherQrcode;
    private List<NetClass> list;
    private List<String> imageList;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherQrcode() {
        return teacherQrcode;
    }

    public void setTeacherQrcode(String teacherQrcode) {
        this.teacherQrcode = teacherQrcode;
    }

    public List<NetClass> getList() {
        return list;
    }

    public void setList(List<NetClass> list) {
        this.list = list;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
