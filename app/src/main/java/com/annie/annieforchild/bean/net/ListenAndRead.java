package com.annie.annieforchild.bean.net;

/**

 */
public class ListenAndRead {

    private int isshow; //0：空
    private String path;
    private int classid;
    private int type;
    private int classify;
    private String week;
    private String username;

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public int getIsshow() {
        return isshow;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public int getClassify() {
        return classify;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek() {
        return week;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

